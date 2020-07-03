package com.milu.ats.dal.repository;

import com.milu.ats.dal.entity.JobPostDO;
import com.milu.ats.dal.entity.vo.JobPostVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author max.chen
 * @class
 */
public interface JobPostRepository extends JpaRepository<JobPostDO, Integer> {

    @Query(value = "select p from JobPostDO p where p.jobId = :jobId and p.resumeId = :resumeId")
    List<JobPostDO> selectByJobIdAndResumeId(@Param("jobId") Integer jobId, @Param("resumeId") Integer resumeId);

    /**
     * 统计职位候选人投递简历数量
     * @param jobIds
     * @return
     */
    @Query(value = "select new com.milu.ats.dal.entity.vo.JobPostVO(p.jobId, 0, count(1)) from JobPostDO p where p.jobId in(:jobIds) group by p.jobId")
    List<JobPostVO> totalByJobIds(@Param("jobIds") List<Integer> jobIds);

    /**
     * 统计职位不同阶段投递简历数量
     * @param jobId
     * @return
     */
    @Query(value = "select new com.milu.ats.dal.entity.vo.JobPostVO(p.jobId, p.snap, count(1)) from JobPostDO p where p.jobId = :jobId group by p.jobId, p.snap")
    List<JobPostVO> totalByJobIdAndSnap(@Param("jobId") Integer jobId);
}
