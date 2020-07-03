package com.milu.ats.bean.pojo;

import com.milu.ats.dal.entity.EducationDO;
import com.milu.ats.dal.entity.ExperienceDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;

/**
 * @author max.chen
 * @class
 */
@Data
@AllArgsConstructor
public class ResumeEducationAO {
    @ApiModelProperty(value = "学校名称", notes = "清华大学")
    private String college;
    @ApiModelProperty(value = "专业", notes = "生命科学")
    private String subject;
    @ApiModelProperty(value = "学位", notes = "学士")
    private String degree;
    @ApiModelProperty(value = "开始时间", notes = "2016-01")
    private String start;
    @ApiModelProperty(value = "结束时间", notes = "2018-01")
    private String end;
    @ApiModelProperty(value = "备注", notes = "xxxxx")
    private String content;

    public static ResumeEducationAO fromDO(EducationDO entity, boolean isAll){
        return entity == null? null : new ResumeEducationAO(
                entity.getCollege(), entity.getSubject(), entity.getDegree(), entity.getStart(), entity.getEnd(),
                isAll? entity.getContent() : ""
        );
    }
}
