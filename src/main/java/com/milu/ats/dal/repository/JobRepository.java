package com.milu.ats.dal.repository;

import com.milu.ats.dal.entity.JobDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author max.chen
 * @class
 */
public interface JobRepository extends JpaRepository<JobDO, Integer> {

    /**
     * 切换 职位 开放状态
     * @param id
     * @param snap
     * @param updater
     * @return
     */
    @Modifying(clearAutomatically = true)
    @Transactional(rollbackFor = Exception.class)
    @Query("update JobDO j set j.snap =:snap, j.updater = :updater where j.id =:id ")
    int switchSnap(@Param("id") int id, @Param("snap") int snap,  @Param("updater") String updater);

}
