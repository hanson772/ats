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
@Table(name = "ats_set",
        indexes = {
                @Index(name = "IDX_set_type", columnList = "set_type")
        },
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"set_type", "set_code"})
        }
)
@org.hibernate.annotations.Table(appliesTo = "ats_set", comment = "设置明细表")
public class SetDO extends AuditingDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //对应 ESet.type
    @Column(name = "set_type", columnDefinition = "int NOT NULL COMMENT 'set类型'")
    private Integer type;

    @Column(name = "set_display", columnDefinition = "nvarchar(100) NOT NULL COMMENT 'set名称'")
    private String display;

    @Column(name = "set_code", columnDefinition = "nvarchar(100) NOT NULL COMMENT 'set编码'")
    private String code;

    @Column(name = "set_description", columnDefinition = "nvarchar(200) NULL COMMENT 'set描述'")
    private String description;
}