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
@Table(name = "ats_resume_experience",
        indexes = {
                @Index(name = "IDX_resumeId", columnList = "resume_id")
        }
)
@org.hibernate.annotations.Table(appliesTo = "ats_resume_experience", comment = "简历-工作经历表")
public class ExperienceDO extends AuditingDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "resume_id", columnDefinition = "int(20) NOT NULL COMMENT '对应简历表Id'")
    private Integer resumeId;

    @Column(name = "exp_company", columnDefinition = "nvarchar(100) NOT NULL COMMENT '公司名称'")
    private String company;

    @Column(name = "exp_job", columnDefinition = "nvarchar(100) NOT NULL COMMENT '岗位名称'")
    private String job;

    @Column(name = "exp_dept", columnDefinition = "nvarchar(100) NULL COMMENT '所在部门'")
    private String dept;

    @Column(name = "exp_start", columnDefinition = "nvarchar(20) NOT NULL COMMENT '开始时间'")
    private String start;

    @Column(name = "exp_end", columnDefinition = "nvarchar(20) NULL COMMENT '结束时间'")
    private String end;

    @Column(name = "exp_content", columnDefinition = "text(2000) NULL COMMENT '工作说明'")
    private String content;

    @Column(name = "exp_comment", columnDefinition = "nvarchar(200) NULL COMMENT '备注'")
    private String comment;
}