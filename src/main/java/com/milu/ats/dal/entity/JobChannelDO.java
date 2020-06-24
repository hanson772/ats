package com.milu.ats.dal.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author max.chen
 * @class
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ats_job_channel",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"job_id", "channel_id"})
}
)
@org.hibernate.annotations.Table(appliesTo = "ats_job_channel", comment = "职位关联渠道表")
public class JobChannelDO extends AuditingDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "job_id", columnDefinition = "int(20) NOT NULL COMMENT '关联job'")
    private Integer jobId;

    @Column(name = "channel_id", columnDefinition = "smallint(5) NOT NULL COMMENT '渠道'")
    private Integer channelId;

    @Column(name = "channel_age", columnDefinition = "smallint(2) NOT NULL COMMENT '年龄要求'")
    private Integer age;

    @Column(name = "channel_welfare", columnDefinition = "nvarchar(200) NOT NULL COMMENT '福利'")
    private String welfare;

    @Column(name = "job_snap", columnDefinition = "tinyint(1) NULL DEFAULT 0 COMMENT '职位开放状态1、0'")
    private Integer snap;

}