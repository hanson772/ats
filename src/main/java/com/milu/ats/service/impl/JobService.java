package com.milu.ats.service.impl;

import com.milu.ats.bean.enums.ELive;
import com.milu.ats.bean.enums.EPosition;
import com.milu.ats.bean.enums.ERole;
import com.milu.ats.bean.pojo.EMessage;
import com.milu.ats.bean.pojo.Employee;
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
import com.milu.ats.util.Pooler;
import com.milu.ats.util.Tools;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.concurrent.CompletableFuture;
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

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 搜索职位
     * @param e
     * @param request
     * @return
     */
    @Override
    public PageResponse<JobSearchResponse> searchByEmployee(Employee e, JobSearchRequest request){
        List<JobSearchResponse> result = new ArrayList<>();
        Integer pageNo = 1;
        Integer pageSize = 10;
        Integer count = 0;
        if (request != null) {
            pageNo = request.getPageNo() == null ? pageNo : request.getPageNo();
            pageSize = request.getPageSize() == null ? pageSize : request.getPageSize();
            try {
                TypedQuery<JobDO> countQuery = buildPurOrderQuery(e, request);
                CompletableFuture<Integer> countTask = Pooler.async(() -> {
                    return countQuery.getResultList().size();
                });

                TypedQuery<JobDO> query = buildPurOrderQuery(e, request);
                query.setFirstResult(pageSize * (pageNo - 1));
                query.setMaxResults(pageSize);
                result = toSearchResponse(query.getResultList());
                count = countTask.get();
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error("搜索Job职位出错：{}", ex.getMessage());
                Assert.isTrue(false, ex.getMessage());
            } finally {
                entityManager.close();
            }
        }

        PageResponse csr = new PageResponse();
        csr.setCount(count);
        csr.setList(result);
        csr.setPageNo(pageNo);
        csr.setPageSize(pageSize);
        return csr;
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
        jobPersonnelRepository.removeByJobId(entity.getId());
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
        jobRepository.switchSnap(entity.getId(), isOpen ? ELive.ENABLE.getCode() : ELive.DISABLE.getCode(), e.getOperator());
        log.info("职位-{}-更新开放状态-{}-{}", entity.getDisplay(), entity.getSnap() ? "开启" :"关闭", e.getOperator());
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
     * @return
     */
    private TypedQuery<JobDO> buildPurOrderQuery(Employee e, JobSearchRequest request){
        StringBuilder sb = new StringBuilder();
        Map<String, Object> paras = new HashMap<>(12);
        sb.append("select j from JobDO j ");
        switch (e.getActive()){
            case Recruiter:
                sb.append("inner join JobPersonnelDO p on p.jobId = j.id and j.account =:account and j.role in (:roles)");
                paras.put("account", e.getAccount());
                paras.put("roles", Arrays.asList(EPosition.Recruiter.getCode(), ERole.Assistant.getCode()));
                break;
            case Interviewer:
                sb.append("inner join JobPersonnelDO p on p.jobId = j.id and j.account =:account and j.role =:roles");
                paras.put("account", e.getAccount());
                paras.put("roles", EPosition.Interviewer.getCode());
                break;
            case Assistant:
                sb.append("inner join JobPersonnelDO p on p.jobId = j.id and j.account =:account and j.role =:roles");
                paras.put("account", e.getAccount());
                paras.put("roles", EPosition.Assistant.getCode());
                break;
            default:
                break;
        }
        sb.append("where j.live = 1");

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
        if(request.getCategory() != null && request.getCategory() > 0){
            sb.append(" and j.category = :category");
            paras.put("category", request.getCategory());
        }

        sb.append(" order by j.created desc");
        TypedQuery<JobDO> query = entityManager.createQuery(sb.toString(), JobDO.class);
        for (String param : paras.keySet()) {
            query.setParameter(param, paras.get(param));
        }

        return query;
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
            Map<Integer, Integer> postMap = jobPostRepository.totalByJobIds(jobIds).stream()
                    .collect(Collectors.toMap(JobPostVO::getJobId, JobPostVO::getPost));;
            Map<String, String> personnelMap = jobPersonnelRepository.selectGroupRoleByJobIds(jobIds).stream()
                    .collect(Collectors.toMap(v-> v.getJobId()+"-"+v.getRole(), JobPersonnelVO::getDisplay ));


            result.forEach(r->{
                JobSearchResponse response = JobSearchResponse.fromDO(r);
                // 设置候选人数量
                response.setCandidateQuantity(postMap.getOrDefault(r.getId(), 0));
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
    private JobDO findById(int jobId){
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
        JobDO entity = jobId > 0 ? jobRepository.findById(jobId).orElse(null) : null;
        Assert.notNull(entity, EMessage.JOB_NOT_EXIST.show());
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
