package com.milu.ats.dal.repository;

import com.milu.ats.dal.entity.ResumeDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author max.chen
 * @class
 */
public interface ResumeRepository extends JpaRepository<ResumeDO, Integer> {
    @Query("select r from ResumeDO r where r.name =:name and r.mobile =:mobile and r.sex = :sex")
    Optional<ResumeDO> selectByNameAndMobile(@Param("name") String name, @Param("mobile") String mobile, @Param("sex") String sex);
}
