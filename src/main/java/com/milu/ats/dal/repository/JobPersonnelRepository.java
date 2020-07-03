package com.milu.ats.dal.repository;

import com.milu.ats.dal.entity.JobPersonnelDO;
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
    @Query(value = "select p.job_id, p.per_role, GROUP_CONCAT(p.per_display)" +
            " from ats_job_personnel p where p.job_id in(:jobIds) group by p.job_id, p.per_role", nativeQuery = true)
    List<Object[]> selectGroupRoleByJobIds(@Param("jobIds") List<Integer> jobIds);

    @Query(value = "select p from JobPersonnelDO p where p.jobId =:jobId")
    List<JobPersonnelDO> selectByJobId(@Param("jobId") Integer jobId);

    @Modifying(clearAutomatically = true)
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "delete from JobPersonnelDO p where p.jobId = :jobId")
    int removeByJobId(@Param("jobId") Integer jobId);

}
