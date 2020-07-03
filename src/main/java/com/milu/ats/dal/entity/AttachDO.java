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
@Table(name = "ats_attach",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"attach_code"})}
)
@org.hibernate.annotations.Table(appliesTo = "ats_attach", comment = "附件表")
public class AttachDO extends AuditingDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "attach_code", columnDefinition = "nvarchar(150) NOT NULL COMMENT '附件唯一字段'")
    private String code;

    @Column(name = "attach_display", columnDefinition = "nvarchar(200) NOT NULL COMMENT '附件显示名称'")
    private String display;

    @Column(name = "attach_mime", columnDefinition = "nvarchar(40) NOT NULL COMMENT '附件mime'")
    private String mime;

    @Column(name = "attach_size", columnDefinition = "int NOT NULL COMMENT '附件大小'")
    private Long size;
}