package com.milu.ats.dal.repository;

import com.milu.ats.dal.entity.JobPostDO;
import com.milu.ats.dal.entity.vo.JobPostVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author max.chen
 * @class
 */
public interface JobPostRepository extends JpaRepository<JobPostDO, Integer> {
    /**
     * 统计候选人投递简历数量
     * @param jobIds
     * @return
     */
    @Query(value = "select new com.milu.ats.dal.entity.vo.JobPostVO(p.job_id, sum(1)) from ats_job_post p where p.job_id in(:jobIds) group by p.job_id", nativeQuery = true)
    List<JobPostVO> totalByJobIds(@Param("jobIds") List<Integer> jobIds);
}
