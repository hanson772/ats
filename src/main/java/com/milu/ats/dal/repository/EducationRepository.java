package com.milu.ats.dal.repository;

import com.milu.ats.dal.entity.EducationDO;
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
public interface EducationRepository extends JpaRepository<EducationDO, Integer> {
    @Query("select e from EducationDO e where e.resumeId = :resumeId")
    List<EducationDO> selectByResumeId(@Param("resumeId") Integer resumeId);

    @Modifying(clearAutomatically = true)
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "delete from EducationDO e where e.resumeId = :resumeId")
    int removeByResumeId(@Param("resumeId") Integer resumeId);
}
