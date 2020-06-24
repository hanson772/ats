package com.milu.ats.dal.repository;

import com.milu.ats.dal.entity.SetDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author max.chen
 * @class
 */
public interface SetRepository extends JpaRepository<SetDO, Integer> {
    @Modifying(clearAutomatically = true)
    @Transactional(rollbackFor = Exception.class)
    @Query("update SetDO s set s.live =:newlive, s.updater = :updater where s.id =:id and s.live =:oldlive")
    int switchSetLive(@Param("id") int id, @Param("oldlive") int oldlive, @Param("newlive") int newlive, @Param("updater") String updater);

}
