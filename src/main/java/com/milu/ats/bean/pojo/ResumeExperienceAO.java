package com.milu.ats.bean.pojo;

import com.milu.ats.dal.entity.ExperienceDO;
import com.milu.ats.dal.repository.ExperienceRepository;
import com.milu.ats.util.Tools;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;

/**
 * @author max.chen
 * @class
 */
@Data
@AllArgsConstructor
public class ResumeExperienceAO {
    @ApiModelProperty(value = "公司名称", notes = "中国电力")
    private String company;
    @ApiModelProperty(value = "岗位名称", notes = "工程师")
    private String job;
    @ApiModelProperty(value = "所在部门", notes = "男/女")
    private String dept;
    @ApiModelProperty(value = "开始时间", notes = "2016-01")
    private String start;
    @ApiModelProperty(value = "结束时间", notes = "2018-01")
    private String end;
    @ApiModelProperty(value = "所在部门", notes = "工作说明")
    private String content;
    @ApiModelProperty(value = "备注", notes = "xxxxxx")
    private String comment;

    public static ResumeExperienceAO fromDO(ExperienceDO entity, boolean isAll){
        return entity == null? null : new ResumeExperienceAO(
                entity.getCompany(), entity.getJob(), entity.getDept(), entity.getStart(), entity.getEnd(),
                isAll? entity.getContent() : "",  isAll? entity.getComment(): ""
        );
    }
}
