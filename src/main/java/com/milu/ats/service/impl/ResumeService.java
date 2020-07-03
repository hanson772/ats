package com.milu.ats.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.milu.ats.bean.enums.EAction;
import com.milu.ats.bean.enums.EFrom;
import com.milu.ats.bean.enums.EPostSnap;
import com.milu.ats.bean.enums.EPostState;
import com.milu.ats.bean.pojo.*;
import com.milu.ats.bean.request.ResumeSearchRequest;
import com.milu.ats.bean.response.PageResponse;
import com.milu.ats.bean.response.ResumeResponse;
import com.milu.ats.bean.response.ResumeSearchResponse;
import com.milu.ats.dal.entity.*;
import com.milu.ats.dal.entity.vo.JobPostVO;
import com.milu.ats.dal.entity.vo.ResumeSearchVO;
import com.milu.ats.dal.repository.EducationRepository;
import com.milu.ats.dal.repository.ExperienceRepository;
import com.milu.ats.dal.repository.JobPostRepository;
import com.milu.ats.dal.repository.ResumeRepository;
import com.milu.ats.service.IAttachService;
import com.milu.ats.service.IJobService;
import com.milu.ats.service.IResumeService;
import com.milu.ats.service.ISearchService;
import com.milu.ats.util.Tools;
import jdk.nashorn.internal.AssertsEnabled;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.RequestGroupInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author max.chen
 * @class
 */
@Service
@Slf4j
public class ResumeService implements IResumeService {
    @Autowired
    ResumeRepository resumeRepository;
    @Autowired
    ExperienceRepository experienceRepository;
    @Autowired
    EducationRepository educationRepository;
    @Autowired
    JobPostRepository jobPostRepository;
    @Autowired
    IAttachService attachService;
    @Autowired
    IJobService jobService;
    @Autowired
    ISearchService searchService;

    /**
     * 投递简历
     * @param jobId 职位ID
     * @param aId 附件ID
     */
    @Override
    public void post(int jobId, int aId){
        AttachDO attach = attachService.findById(aId);
        Assert.notNull(attach, EMessage.ATTACH_NOT_EXIST.show());
        JobDO entity = jobService.findById(jobId);

        ResumePackage resumePackage = analyzeByAttach(attach);
        ResumeDO resume = updateResumeByPackage(resumePackage);
        List<JobPostDO> posts = jobPostRepository.selectByJobIdAndResumeId(jobId, resume.getId());

        if(posts == null || posts.isEmpty()){
            // 增加一条投递记录
            jobPostRepository.saveAndFlush(JobPostDO.builder()
                    .jobId(entity.getId())
                    .resumeId(resume.getId())
                    .from(EFrom.Post.getCode())
                    .snap(EPostSnap.Audition.getCode()).build());
        } else {
            //TODO 校验投递简历是否超过限制
        }
    }

    /**
     * 查询职位简历投递情况
     * @param e
     * @param request
     * @return
     */
    @Override
    public PageResponse<ResumeSearchResponse> postResumeSearch(Employee e, ResumeSearchRequest request){
        SearchBase<ResumeSearchVO> searchQuery = buildResumeSqlQuery(e, request);
        searchQuery.setPageNo(request.getPageNo());
        searchQuery.setPageSize(request.getPageSize());

        PageResponse<ResumeSearchVO> csr = searchService.searchByPage(searchQuery);

        PageResponse<ResumeSearchResponse> response = new PageResponse();
        response.setCount(csr.getCount());
        response.setList(csr.getList().stream().map(x-> ResumeSearchResponse.fromVO(x)).collect(Collectors.toList()));
        response.setPageNo(csr.getPageNo());
        response.setPageSize(csr.getPageSize());

        return response;
    }

    /**
     * 统计职位不同阶段的投递数量
     * @param e
     * @param postId
     * @return
     */
    @Override
    public Map<Integer, Long> totalPostInJob(Employee e, int postId){
        JobPostDO post = checkAuthForPost(e, postId);
        List<JobPostVO> posts = jobPostRepository.totalByJobIdAndSnap(post.getJobId());
        return posts.stream().collect(Collectors.toMap(JobPostVO::getSnap, JobPostVO::getCount));
    }

    public void action(Employee e, EAction state, List<Integer> ids){
        //TODO 校验employee 是否有通过权限
        // 查询出投递记录(已投递、淘汰)
        List<Integer> snaps =  Arrays.asList(EPostSnap.Audition.getCode(), EPostSnap.Out.getCode());
        List<JobPostDO> posts = jobPostRepository.findAllById(ids).stream().filter(p->snaps.contains(p.getSnap())).collect(Collectors.toList());

    }

    /**
     * 查看简历详情
     * @param resumeId
     * @return
     */
    @Override
    public ResumeResponse detail(int resumeId){
        ResumeDO resumeDO = resumeRepository.findById(resumeId).orElse(null);
        if(resumeDO != null){
            List<ExperienceDO> experiences = experienceRepository.selectByResumeId(resumeId);
            List<EducationDO> educations = educationRepository.selectByResumeId(resumeId);

            return ResumeResponse.builder().id(Tools.idEncode(resumeDO.getId()))
                    .body(ResumeAO.fromDO(resumeDO))
                    .experiences(experiences.stream().map(ex-> ResumeExperienceAO.fromDO(ex, true)).collect(Collectors.toList()))
                    .educations(educations.stream().map(ed-> ResumeEducationAO.fromDO(ed, true)).collect(Collectors.toList()))
                    .build();
        }
        return null;
    }

    /**
     * 根据package，更新简历库中的简历内容
     * @param pack
     */
    @Transactional
    private ResumeDO updateResumeByPackage(ResumePackage pack){
        ResumeDO resume = pack.getBody();
        Optional<ResumeDO> optional = resumeRepository.selectByNameAndMobile(resume.getName(), resume.getMobile(), resume.getSex());
        if(optional.isPresent()){
            // 更新简历

            ResumeDO old = optional.get();
            resume.setId(old.getId());
            experienceRepository.removeByResumeId(old.getId());
            educationRepository.removeByResumeId(old.getId());
        }

        resumeRepository.saveAndFlush(resume);
        final int rid = resume.getId();
        List<ExperienceDO> experiences = pack.getExperiences().stream().map(e-> {
            e.setResumeId(rid);
            return e;
        }).collect(Collectors.toList());
        experienceRepository.saveAll(experiences);
        List<EducationDO> educations = pack.getEducations().stream().map(e-> {
            e.setResumeId(rid);
            return e;
        }).collect(Collectors.toList());
        educationRepository.saveAll(educations);
        return resume;
    }

    /**
     * 构建查询简历投递sql
     * @param e
     * @param request
     * @return
     */
    private SearchBase<ResumeSearchVO> buildResumeSqlQuery(Employee e, ResumeSearchRequest request){
        StringBuilder sb = new StringBuilder();
        Map<String, Object> paras = new HashMap<>(20);
        sb.append("select new com.milu.ats.dal.entity.vo.ResumeSearchVO(" +
                "p.jobId, r.id, r.name, r.mobile, r.sex, r.email, r.birthday, r.city, r.degree, r.seniority, p.from, p.snap, p.created, r.experiences, r.educations) " +
                " from JobPostDO p, ResumeDO r where r.id = p.resumeId ");

        // 查找投递状态
        EPostState state = EPostState.Unknow.getByCode(request.getState()!= null ? request.getState().intValue() : EPostState.FirstFilter.getCode());
        sb.append(" and p.snap in(:snaps)");
        paras.put("snaps", state.selectSnap());
        // 查找投递职位
        if(StringUtils.hasText(request.getJobId())){
            sb.append(" and p.jobId =:jobId");
            paras.put("jobId", Tools.idDecode(request.getJobId()));
        }
        // 关键字查找
        if(StringUtils.hasText(request.getKey())){
            if(Tools.regexNumber(request.getKey()) || Tools.regexPhone(request.getKey())){
                // 查找手机号
                sb.append(" and r.mobile like :mobile");
                paras.put("mobile", "%" + request.getKey() + "%");
            } else if(Tools.regexEmail(request.getKey())){
                // 查找邮箱
                sb.append(" and r.email like :email");
                paras.put("email", "%" + request.getKey() + "%");
            } else {
                // 模糊匹配 姓名，公司，学校，职位
                sb.append(" and (r.name like :key or r.experiences like :key or r.educations like :key)");
                paras.put("key", "%" + request.getKey() + "%");
            }
        }
        // 公司查找
        if(StringUtils.hasText(request.getCompany())){
            sb.append(" and r.experiences like :company");
            paras.put("company", "%" + request.getCompany() +"%");
        }
        // 工作年限
        if(StringUtils.hasText(request.getSeniority())){
            sb.append(" and r.seniority =:seniority");
            paras.put("seniority", request.getSeniority());
        }
        // 职位模糊搜索
        if(StringUtils.hasText(request.getJob())){
            sb.append(" and r.experiences like :job");
            paras.put("job", "%"+ request.getJob() +"%");
        }
        // 学校模糊搜索
        if(StringUtils.hasText(request.getCollege())){
            sb.append(" and r.educations =:educations");
            paras.put("educations", "%"+ request.getCollege() + "%");
        }
        // 最高学历
        if(StringUtils.hasText(request.getDegree())){
            sb.append(" and r.degree =:degree");
            paras.put("degree", request.getDegree());
        }
        // 渠道
        if(StringUtils.hasText(request.getFrom())){
            sb.append(" and p.from =:from");
            paras.put("from", request.getFrom());
        }

        sb.append(" order by p.id desc");

        return new SearchBase<ResumeSearchVO>(sb, paras, ResumeSearchVO.class);
    }

    /**
     * 上传附件解析成简历
     * @param file
     * @return
     */
    private ResumePackage analyzeByFile(MultipartFile file){
        ResumePackage resumePackage = new ResumePackage();
        //TODO


        return resumePackage;
    }
    private ResumePackage analyzeByAttach(AttachDO attach){
        ResumePackage resumePackage = new ResumePackage();

        List<ExperienceDO> experiences = new ArrayList<>();
        experiences.add(ExperienceDO.builder().company("华为").job("开发工程师").dept("技术研究中心").start("2018年5月").end("2020年5月").content("开发工程师").build());
        experiences.add(ExperienceDO.builder().company("中兴").job("开发工程师").dept("技术研究中心").start("2016年5月").end("2018年5月").content("开发工程师").build());
        resumePackage.setExperiences(experiences);
        // 简化工作经历，写进resume-body中
        List<ResumeExperienceAO> simpleExperience = experiences.stream().map(ex-> ResumeExperienceAO.fromDO(ex, false)).collect(Collectors.toList());


        List<EducationDO> educations = new ArrayList<>();
        educations.add(EducationDO.builder().college("上海理工大学").degree("大学本科").subject("计算机科学与技术")
                .start("2012-09-01" ).end("2016-07-01" ).build());
        resumePackage.setEducations(educations);
        // 简化教育经历，写进resume-body中
        List<ResumeEducationAO> simpleEducations = educations.stream().map(ed-> ResumeEducationAO.fromDO(ed, false)).collect(Collectors.toList());

        ResumeDO body = ResumeDO.builder()
                .name("张无忌").email("wuji.zhang@xxx.com").mobile("13900000001").sex("男")
                .birthday(Tools.dateFrom(Tools.Date_Format,"1995-01-01" )).city("上海").seniority("1-5年")
                .experiences(JSONArray.toJSONString(simpleExperience))
                .educations(JSONArray.toJSONString(simpleEducations))
                .build();
        resumePackage.setBody(body);

        //TODO
        return resumePackage;
    }

    /**
     * 校验是否有权限查看职位投递简历
     * @param e
     * @param postId
     * @return
     */
    private JobPostDO checkAuthForPost(Employee e, int postId){
        JobPostDO entity = postId > 0 ? jobPostRepository.findById(postId).orElse(null) : null;
        Assert.notNull(entity, EMessage.OBJECT_NOT_EXIST.show());
        return entity;
    }

    @Data
    class ResumePackage{
        // 个人信息
        ResumeDO body;
        // 工作经历
        List<ExperienceDO> experiences;
        // 教育经历
        List<EducationDO> educations;
    }
}
