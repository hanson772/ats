package com.milu.ats.dal.repository;

import com.milu.ats.dal.entity.JobChannelDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author max.chen
 * @class
 */
public interface JobChannelRepository extends JpaRepository<JobChannelDO, Integer> {
    @Query("select c from JobChannelDO c where c.jobId = :jobId and c.channelId = :channelId")
    Optional<JobChannelDO> selectChannelByJobIdAndChannelId(@Param("jobId") Integer jobId, @Param("channelId") Integer channelId);

    @Query("select c from JobChannelDO c where c.jobId = :jobId")
    List<JobChannelDO> selectByJobId(@Param("jobId") Integer jobId);
}
