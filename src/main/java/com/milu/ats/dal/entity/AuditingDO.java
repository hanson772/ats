package com.milu.ats.dal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author max.chen
 * @class
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"gmt_create_time", "gmt_update_time", "live"},
        allowGetters = true
)
public abstract class AuditingDO implements Serializable {
    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "gmt_create_time", columnDefinition = "datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    @CreatedDate
    private Date created;

    /**
     * 创建人
     */
    @Column(name = "gmt_create_by", columnDefinition = "nvarchar(50) COMMENT '创建人'")
    private String creator;

    /**
     * 更新时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "gmt_update_time", columnDefinition = "datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'")
    @LastModifiedDate
    private Date updated;

    /**
     * 更新人
     */
    @Column(name = "gmt_update_by", columnDefinition = "nvarchar(50) COMMENT '更新人'")
    private String updater;

    /**
     * 生存状态
     */
    @Column(name = "live", nullable = false, columnDefinition = "smallint(2) default 1 COMMENT '生存状态（1：有效，0：失效）'")
    private Integer live = 1;

    /**
     * 未命名字段
     */
    @Column(name = "unnamed1", insertable = false, updatable = false, columnDefinition = "nvarchar(200) COMMENT '未命名字段'")
    private String unnamed1;
    /**
     * 未命名字段
     */
    @Column(name = "unnamed1", insertable = false, updatable = false, columnDefinition = "nvarchar(200) COMMENT '未命名字段'")
    private String unnamed2;

}