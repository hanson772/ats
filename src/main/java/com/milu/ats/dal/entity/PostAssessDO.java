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
@Table(name = "ats_post_assess",
        indexes = {
                @Index(name = "IDX_job_id", columnList = "job_id"),
                @Index(name = "IDX_resume_id", columnList = "resume_id")
        }
)
@org.hibernate.annotations.Table(appliesTo = "ats_post_assess", comment = "简历评价表")
public class PostAssessDO extends AuditingDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "job_id", columnDefinition = "int(20) NOT NULL COMMENT '关联job'")
    private Integer jobId;

    @Column(name = "resume_id", columnDefinition = "int(20) NOT NULL COMMENT '关联投递简历'")
    private Integer resumeId;

    @Column(name = "assess_content", columnDefinition = "text(1000) NOT NULL COMMENT '评价描述'")
    private Integer content;

    @Column(name = "assess_by_position", columnDefinition = "smallint(3) NOT NULL COMMENT '评价角色'")
    private Integer position;

    @Column(name = "post_snap", columnDefinition = "smallint(3) NOT NULL COMMENT '评价时处于投递阶段'")
    private Integer snap;
}