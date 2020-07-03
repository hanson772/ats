package com.milu.ats.service.impl;

import com.milu.ats.bean.pojo.Easy;
import com.milu.ats.bean.enums.EPosition;
import com.milu.ats.bean.enums.ERole;
import com.milu.ats.bean.pojo.EMessage;
import com.milu.ats.bean.pojo.Employee;
import com.milu.ats.bean.pojo.SearchBase;
import com.milu.ats.bean.request.*;
import com.milu.ats.bean.response.JobResponse;
import com.milu.ats.bean.response.JobSearchResponse;
import com.milu.ats.bean.response.PageResponse;
import com.milu.ats.dal.entity.JobChannelDO;
import com.milu.ats.dal.entity.JobDO;
import com.milu.ats.dal.entity.JobPersonnelDO;
import com.milu.ats.dal.entity.vo.JobPersonnelVO;
import com.milu.ats.dal.entity.vo.JobPostVO;
import com.milu.ats.dal.repository.JobChannelRepository;
import com.milu.ats.dal.repository.JobPersonnelRepository;
import com.milu.ats.dal.repository.JobPostRepository;
import com.milu.ats.dal.repository.JobRepository;
import com.milu.ats.service.IJobService;
import com.milu.ats.service.ISearchService;
import com.milu.ats.util.Tools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author max.chen
 * @class
 */
@Service
@Slf4j
public class JobService implements IJobService {
    @Autowired
    JobRepository jobRepository;
    @Autowired
    JobPersonnelRepository jobPersonnelRepository;
    @Autowired
    JobPostRepository jobPostRepository;
    @Autowired
    JobChannelRepository jobChannelRepository;

    @Autowired
    ISearchService searchService;
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 搜索职位
     * @param e
     * @param request
     * @return
     */
    @Override
    public PageResponse<JobSearchResponse> searchByEmployee(Employee e, JobSearchRequest request, boolean isEdit){
        SearchBase<JobDO> searchQuery = buildJobQuery(e, request, isEdit);
        searchQuery.setPageNo(request.getPageNo());
        searchQuery.setPageSize(request.getPageSize());

        PageResponse<JobDO> csr = searchService.searchByPage(searchQuery);

        PageResponse<JobSearchResponse> response = new PageResponse();
        response.setCount(csr.getCount());
        response.setList(toSearchResponse(csr.getList()));
        response.setPageNo(csr.getPageNo());
        response.setPageSize(csr.getPageSize());

        return response;
    }

    /**
     * 增加、更新职位
     * @param e
     * @param jobId
     * @param request
     */
    @Override
    public void saveJob(Employee e, int jobId, JobRequest request){
        JobDO entity = null;
        if(jobId > 0){
            entity = checkAuthForJob(e, jobId);
            entity.setUpdater(e.getOperator());
        } else {
            // 管理员或招聘官才可以创建职位
            checkRoleForJob(e);
            entity = JobDO.builder().build();
            entity.setCreator(e.getOperator());
        }

        BeanUtils.copyProperties(request, entity);
        entity.setSnap(false);
        jobRepository.saveAndFlush(entity);
        log.info("职位-{}-保存-{}", entity.getDisplay(), e.getOperator());
    }

    /**
     * 保存负责人列表
     * @param e
     * @param jobId
     * @param request
     */
    @Override
    public void savePersonnel(Employee e, int jobId, JobPersonnelRequest request){
        JobDO entity = checkAuthForJob(e, jobId);
        List<JobPersonnelDO> personnelDOS = new ArrayList<>();

        request.getRecruiter().forEach(x->{
            personnelDOS.add(JobPersonnelDO.builder()
                    .jobId(entity.getId()).display(x.getValue()).account(x.getKey()).role(EPosition.Recruiter.getCode())
                    .build());
        });
        request.getBusinesser().forEach(x->{
            personnelDOS.add(JobPersonnelDO.builder()
                    .jobId(entity.getId()).display(x.getValue()).account(x.getKey()).role(EPosition.Businesser.getCode())
                    .build());
        });
        request.getInterviewer().forEach(x->{
            personnelDOS.add(JobPersonnelDO.builder()
                    .jobId(entity.getId()).display(x.getValue()).account(x.getKey()).role(EPosition.Interviewer.getCode())
                    .build());
        });
        request.getAssistant().forEach(x->{
            personnelDOS.add(JobPersonnelDO.builder()
                    .jobId(entity.getId()).display(x.getValue()).account(x.getKey()).role(EPosition.Assistant.getCode())
                    .build());
        });

        // 移除之前设置的负责人列表
        int n = jobPersonnelRepository.removeByJobId(entity.getId());
        if(!personnelDOS.isEmpty()) {
            // 保存新的负责人列表
            jobPersonnelRepository.saveAll(personnelDOS);
        }
        log.info("职位-{}-更新负责人-{}", entity.getDisplay(), e.getOperator());
    }

    /**
     * 保存更新-职位渠道详情
     * @param e
     * @param jobId
     * @param request
     */
    @Override
    public void saveChannel(Employee e, int jobId, JobChannelRequest request){
        JobDO entity = checkAuthForJob(e, jobId);
        Optional<JobChannelDO> exist = jobChannelRepository.selectChannelByJobIdAndChannelId(entity.getId(), request.getChannelId());
        JobChannelDO channel = exist.isPresent()? exist.get() : JobChannelDO.builder().build();
        channel.setAge(request.getAge());
        channel.setChannelId(request.getChannelId());
        channel.setSnap(request.isSnap());
        channel.setCreator(e.getOperator());

        jobChannelRepository.saveAndFlush(channel);
        log.info("职位-{}-保存渠道-{}", entity.getDisplay(), e.getOperator());
    }

    /**
     * 切换职位开放状态
     * @param e
     * @param jobId
     * @param isOpen
     */
    @Override
    public void change(Employee e, int jobId, boolean isOpen){
        JobDO entity = checkAuthForJob(e, jobId);
        jobRepository.switchSnap(entity.getId(), isOpen, e.getOperator());
        log.info("职位-{}-更新开放状态-{}-{}", entity.getDisplay(), entity.getSnap() ? "开启" :"关闭", e.getOperator());
    }

    @Override
    public JobDO findById(int jobId){
        return checkJobExist(jobId);
    }




    /**
     *
     * @param e
     * @param jobId
     * @param isAll
     * @return
     */
    @Override
    public JobResponse detail(Employee e, int jobId, boolean isAll){
        JobDO entity = checkReadAuthForJob(e, jobId);
        JobResponse response = JobResponse.builder()
                .id(Tools.idEncode(entity.getId()))
                .body(JobRequest.fromDO(entity))
                .build();
        if(isAll){
            // 查询所有负责人列表， 按角色赋值
            List<JobPersonnelDO> personnelS = jobPersonnelRepository.selectByJobId(entity.getId());
            JobPersonnelRequest personnelRequest = JobPersonnelRequest.builder()
                    .recruiter(findEasyByRole(personnelS, EPosition.Recruiter))
                    .businesser(findEasyByRole(personnelS, EPosition.Businesser))
                    .interviewer(findEasyByRole(personnelS, EPosition.Interviewer))
                    .assistant(findEasyByRole(personnelS, EPosition.Assistant))
                    .build();
            response.setPersonnel(personnelRequest);
            // 查询所有渠道列表，组合
            List<JobChannelDO> channels = jobChannelRepository.selectByJobId(entity.getId());
            response.setChannel(channels.stream().map(c-> JobChannelRequest.fromDO(c)).collect(Collectors.toList()));
        }
        return response;
    }

    /**
     * 构建查询job职位SQL
     * @param e
     * @param request
     * @param isEdit 是否是编辑查询（只查询自己关联的job)
     * @return
     */
    private SearchBase buildJobQuery(Employee e, JobSearchRequest request, boolean isEdit){
        StringBuilder sb = new StringBuilder();
        Map<String, Object> paras = new HashMap<>(20);
        sb.append("select j from JobDO j where j.live = 1 ");

        if(isEdit){
            // 如果是编辑查询，非管理员和招聘负责人不能查询任何职位
            if(e.getActive() != ERole.Recruiter && e.getActive() != ERole.Manager){
                sb.append(" and j.id = 0");
                return new SearchBase<JobDO>(sb, paras, JobDO.class);
            }
        }

        // 查询职位名称
        if(StringUtils.hasText(request.getDisplay())){
            sb.append(" and j.display like :display");
            paras.put("display", request.getDisplay());
        }
        // 查询部门
        if(StringUtils.hasText(request.getDept())){
            sb.append(" and j.dept = :dept");
            paras.put("dept", request.getDept());
        }
        // 查询岗位类别
        if(StringUtils.hasText(request.getCategory())){
            sb.append(" and j.category = :category");
            paras.put("category", request.getCategory());
        }
        // 查询地点
        if(StringUtils.hasText(request.getLocation())){
            sb.append(" and j.location = :location");
            paras.put("location", request.getLocation());
        }

        // 编辑阶段
        if(isEdit){
            // 只能查询自己相关的职位, 管理员查阅所有
            if(e.getActive() == ERole.Recruiter) {
                sb.append(" and j.id in ( select p.jobId from JobPersonnelDO p where p.account =:account)");
                paras.put("account", e.getAccount());
            }
        } else {
            // 非编辑阶段可以查询公开的职位
            sb.append(" and j.snap = 1");
        }

        sb.append(" order by j.created desc");

        return new SearchBase<JobDO>(sb, paras, JobDO.class);
    }

    /**
     * 搜索职位添加相关的候选人数量和负责人显示名
     * @param result
     * @return
     */
    private List<JobSearchResponse> toSearchResponse(List<JobDO> result){
        List<JobSearchResponse> list = new ArrayList<>();
        if(!result.isEmpty()){
            List<Integer> jobIds = result.stream().map(x-> x.getId()).collect(Collectors.toList());
            Map<Integer, Long> postMap = jobPostRepository.totalByJobIds(jobIds).stream()
                    .collect(Collectors.toMap(JobPostVO::getJobId, JobPostVO::getCount));
            Map<String, String> personnelMap = jobPersonnelRepository.selectGroupRoleByJobIds(jobIds).stream()
                    .map(o-> new JobPersonnelVO(Integer.valueOf(o[0].toString()), Integer.valueOf(o[1].toString()), o[2].toString()))
                    .collect(Collectors.toMap(v-> v.getJobId()+"-"+v.getRole(), JobPersonnelVO::getDisplay ));

            result.forEach(r->{
                JobSearchResponse response = JobSearchResponse.fromDO(r);
                // 设置候选人数量
                response.setCandidateQuantity(postMap.getOrDefault(r.getId(), 0L).intValue());
                // 设置招聘负责人
                response.setRecruiter(personnelMap.getOrDefault(r.getId()+"-"+ EPosition.Recruiter.getCode(), ""));
                // 设置业务负责人
                response.setBusinesser(personnelMap.getOrDefault(r.getId()+"-"+ EPosition.Businesser.getCode(), ""));
                // 设置招聘协助人
                response.setAssistant(personnelMap.getOrDefault(r.getId()+"-"+ EPosition.Assistant.getCode(), ""));
                list.add(response);
            });
        }
        return list;
    }

    /**
     * 在负责人列表中找到相关角色的人员
     * @param personnelDOS
     * @param position
     * @return
     */
    private List<Easy> findEasyByRole(List<JobPersonnelDO> personnelDOS, EPosition position){
        return personnelDOS.stream().filter(x-> x.getRole().compareTo(position.getCode()) == 0)
                .map(x-> Easy.get(x.getAccount(), x.getDisplay()))
                .collect(Collectors.toList());
    }

    /**
     * 检验是否可以操作job职位
     * @param
     */
    private JobDO checkJobExist(int jobId){
        JobDO entity = jobId > 0 ? jobRepository.findById(jobId).orElse(null) : null;
        Assert.notNull(entity, EMessage.JOB_NOT_EXIST.show());
        return entity;
    }
    private void checkRoleForJob(Employee e){
        Assert.isTrue(Arrays.asList(ERole.Recruiter, ERole.Manager).contains(e.getActive()), EMessage.AUTH_NO.show());
    }
    private JobDO checkAuthForJob(Employee e, int jobId){
        JobDO entity = findById(jobId);
        // 校验是否可以编辑Job
        checkRoleForJob(e);
        // 如果不是创建者，判断是否是招聘负责人
        if(!entity.getCreator().equals(e.getOperator())){
            List<EPosition> positiones = getPositionForJob(e, jobId);
            Assert.isTrue(positiones!= null && positiones.contains(EPosition.Recruiter), EMessage.AUTH_NO.show());
        }
        return entity;
    }
    private JobDO checkReadAuthForJob(Employee e, int jobId){
        JobDO entity = checkJobExist(jobId);
        //checkAuthForJob(e);

        return entity;
    }


    /**
     * 返回employee 在一个job 中的postion信息
     * @param e
     * @param jobId
     * @return
     */
    private List<EPosition> getPositionForJob(Employee e, int jobId){
        List<JobPersonnelDO> personnelS = jobPersonnelRepository.selectByJobId(jobId);
        return personnelS.stream()
                .filter(p-> p.getAccount().equalsIgnoreCase(e.getAccount()))
                .map(x-> (EPosition)EPosition.Assistant.getByCode(x.getRole()))
                .collect(Collectors.toList());
    }
}
