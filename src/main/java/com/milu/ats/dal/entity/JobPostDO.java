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
@Table(name = "ats_job_post",
        indexes = {
                @Index(name = "IDX_job_id", columnList = "job_id"),
                @Index(name = "IDX_resume_id", columnList = "resume_id")
        }
)
@org.hibernate.annotations.Table(appliesTo = "ats_job_post", comment = "职位投递简历表")
public class JobPostDO extends AuditingDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "job_id", columnDefinition = "int(20) NOT NULL COMMENT '关联job'")
    private Integer jobId;

    @Column(name = "resume_id", columnDefinition = "int(20) NOT NULL COMMENT '关联投递简历'")
    private Integer resumeId;
    // EFrom
    @Column(name = "post_from", columnDefinition = "smallint(3) NOT NULL COMMENT '简历来源'")
    private Integer from;
    // EPostSnap
    @Column(name = "post_snap", columnDefinition = "smallint(3) NOT NULL COMMENT '简历投递阶段'")
    private Integer snap;
    // EPostSnap
    @Column(name = "post_frezz", columnDefinition = "smallint(3) NULL COMMENT '淘汰时的简历投递阶段，用于回复状态'")
    private Integer frezz;
}