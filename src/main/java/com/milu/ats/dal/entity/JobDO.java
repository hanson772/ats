package com.milu.ats.dal.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author max.chen
 * @class
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ats_job",
        indexes = {
                @Index(name = "IDX_job_category", columnList = "job_category")
        }
)
@DynamicUpdate
@org.hibernate.annotations.Table(appliesTo = "ats_job", comment = "职位表")
public class JobDO extends AuditingDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "job_display", columnDefinition = "nvarchar(100) NOT NULL COMMENT '职位显示名称'")
    private String display;

    @Column(name = "job_dept", columnDefinition = "nvarchar(100) NOT NULL COMMENT '所属部门'")
    private String dept;

    @Column(name = "job_category", columnDefinition = "nvarchar(50) NOT NULL COMMENT '岗位类别'")
    private String category;

    @Column(name = "job_rank_", columnDefinition = "nvarchar(50) NOT NULL COMMENT '职级范围'")
    private String rank;

    @Column(name = "job_experience", columnDefinition = "nvarchar(50) NOT NULL COMMENT '工作经验'")
    private String experience;

    @Column(name = "job_education", columnDefinition = "nvarchar(50) NOT NULL COMMENT '学历要求'")
    private String education;

    @Column(name = "job_salary_start", columnDefinition = "nvarchar(100) NULL COMMENT '薪资范围'")
    private String salaryStart;

    @Column(name = "job_salary_end", columnDefinition = "nvarchar(100) NULL COMMENT '薪资范围'")
    private String salaryEnd;

    @Column(name = "job_location", columnDefinition = "nvarchar(50) COMMENT '工作地点'")
    private String location;

    @Column(name = "job_description", columnDefinition = "text(2000) NULL COMMENT '职位描述'")
    private String description;

    @Column(name = "job_snap", columnDefinition = "bit(1) NULL DEFAULT 0 COMMENT '职位开放状态1、0'")
    private Boolean snap;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "close_time", columnDefinition = "datetime NULL COMMENT '职位关闭时间'")
    private Date closed;
}