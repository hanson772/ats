package com.milu.ats.dal.repository;

import com.milu.ats.dal.entity.AttachDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author max.chen
 * @class
 */
public interface AttachRepository extends JpaRepository<AttachDO, Integer> {
    Optional<AttachDO> findByCode(String code);
}
