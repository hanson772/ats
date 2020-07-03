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
@Table(name = "ats_resume_education",
        indexes = {
                @Index(name = "IDX_resumeId", columnList = "resume_id")
        }
)
@org.hibernate.annotations.Table(appliesTo = "ats_resume_education", comment = "简历-教育经历表")
public class EducationDO extends AuditingDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "resume_id", columnDefinition = "int(20) NOT NULL COMMENT '对应简历表Id'")
    private Integer resumeId;

    @Column(name = "edu_college", columnDefinition = "nvarchar(100) NOT NULL COMMENT '学校名称'")
    private String college;

    @Column(name = "edu_subject", columnDefinition = "nvarchar(100) NOT NULL COMMENT '专业'")
    private String subject;
    // Eset type= 105
    @Column(name = "edu_degree", columnDefinition = "nvarchar(20) NOT NULL COMMENT '学位'")
    private String degree;

    @Column(name = "edu_start", columnDefinition = "nvarchar(50) NOT NULL COMMENT '开始时间'")
    private String start;

    @Column(name = "edu_end", columnDefinition = "nvarchar(50) NOT NULL COMMENT '结束时间'")
    private String end;

    @Column(name = "edu_content", columnDefinition = "nvarchar(200) NULL COMMENT '备注'")
    private String content;
}