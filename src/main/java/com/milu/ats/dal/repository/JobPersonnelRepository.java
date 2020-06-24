package com.milu.ats.dal.repository;

import com.milu.ats.dal.entity.JobPersonnelDO;
import com.milu.ats.dal.entity.vo.JobPersonnelVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author max.chen
 * @class
 */
public interface JobPersonnelRepository extends JpaRepository<JobPersonnelDO, Integer> {
    @Query(value = "select new com.milu.ats.dal.entity.vo.JobPersonnelVO(p.job_id, p.role, GROUP_CONCAT(p.display))" +
            " from ats_job_personnel p where p.jobId in(:jobIds) group by p.job_id, p.role", nativeQuery = true)
    List<JobPersonnelVO> selectGroupRoleByJobIds(@Param("jobIds") List<Integer> jobIds);

    @Query(value = "select p from JobPersonnelDO p where p.jobId =:jobId")
    List<JobPersonnelDO> selectByJobId(@Param("jobId") Integer jobId);

    @Modifying(clearAutomatically = true)
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "delete from JobPersonnelDO p where p.jobId = :jobId")
    int removeByJobId(@Param("jobId") Integer jobId);

}
