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
@Table(name = "ats_job_personnel",
        indexes = {
                @Index(name = "IDX_job_id", columnList = "job_id"),
                @Index(name = "IDX_per_account", columnList = "per_account"),
                @Index(name = "IDX_per_role", columnList = "per_role")
        }
)
@org.hibernate.annotations.Table(appliesTo = "ats_job_personnel", comment = "职位相关人员表")
public class JobPersonnelDO extends AuditingDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "job_id", columnDefinition = "int(20) NOT NULL COMMENT '关联job'")
    private Integer jobId;

    @Column(name = "per_display", columnDefinition = "nvarchar(100) NOT NULL COMMENT '人员显示名称'")
    private String display;

    @Column(name = "per_account", columnDefinition = "nvarchar(100) NOT NULL COMMENT '人员账户'")
    private String account;

    /*EPosition*/
    @Column(name = "per_role", columnDefinition = "smallint(5) NOT NULL COMMENT '职位中人员角色'")
    private Integer role;
}