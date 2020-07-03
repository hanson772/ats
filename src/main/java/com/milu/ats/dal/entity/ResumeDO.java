package com.milu.ats.dal.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "ats_resume",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"resume_name", "resume_mobile","resume_sex"})}
)
@org.hibernate.annotations.Table(appliesTo = "ats_resume", comment = "简历主表")
public class ResumeDO extends AuditingDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "resume_name", columnDefinition = "nvarchar(150) NOT NULL COMMENT '候选人名称'")
    private String name;
    @Column(name = "resume_mobile", columnDefinition = "nvarchar(20) NOT NULL COMMENT '候选人名称手机号'")
    private String mobile;
    @Column(name = "resume_sex", columnDefinition = "nvarchar(3) NOT NULL COMMENT '候选人性别'")
    private String sex;
    @Column(name = "resume_email", columnDefinition = "nvarchar(100) NULL COMMENT '候选人邮箱'")
    private String email;
    @Column(name = "resume_birth", columnDefinition = "nvarchar(100) NULL COMMENT '候选人出生日期'")
    private Date birthday;
    @Column(name = "resume_city", columnDefinition = "nvarchar(50) NULL COMMENT '候选人所在城市'")
    private String city;
    // ESET type=106
    @Column(name = "resume_seniority", columnDefinition = "nvarchar(20) NULL COMMENT '候选人工作年限'")
    private String seniority;
    // ESET type=105
    @Column(name = "edu_degree", columnDefinition = "nvarchar(20) NOT NULL COMMENT '最高学历'")
    private String degree;
    @Column(name = "resume_experiences", columnDefinition = "text(2000) NULL COMMENT '候选人工作经历简单描述(用于查询显示)'")
    private String experiences;
    @Column(name = "resume_eudcations", columnDefinition = "text(2000) NULL COMMENT '候选人教育经历简单描述(用于查询显示)'")
    private String educations;
}