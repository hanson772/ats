package com.milu.ats.bean.request;

import com.milu.ats.bean.enums.ELive;
import com.milu.ats.dal.entity.JobDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.sql.Insert;
import org.hibernate.sql.Update;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author max.chen
 * @class
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "职位保存请求实体", description = "保存职位")
public class JobRequest {
    @NotBlank(message = "请填写职位名称", groups = {Insert.class, Update.class})
    @Length(max = 50, message = "职位名称最多支持输入50个字符", groups = {Insert.class, Update.class})
    @ApiModelProperty(value = "职位", notes = "高级产品经理")
    private String display;
    @NotBlank(message = "请选择部门", groups = {Insert.class, Update.class})
    @ApiModelProperty(value = "部门", notes = "技术研究中心")
    private String dept;
    @NotBlank(message = "请选择岗位类别", groups = {Insert.class, Update.class})
    @ApiModelProperty(value = "岗位类别", notes = "111")
    private Integer category;
    @NotBlank(message = "请选择职级范围", groups = {Insert.class, Update.class})
    @ApiModelProperty(value = "职级范围", notes = "111")
    private Integer rank;
    @NotBlank(message = "请选择工作经验", groups = {Insert.class, Update.class})
    @ApiModelProperty(value = "工作经验", notes = "3-5年")
    private Integer experience;
    @NotBlank(message = "请选择学历要求", groups = {Insert.class, Update.class})
    @ApiModelProperty(value = "学历要求", notes = "111")
    private Integer education;
    @NotBlank(message = "请填写部门名称", groups = {Insert.class, Update.class})
    @ApiModelProperty(value = "薪资范围", notes = "20k")
    private String salaryStart;
    @ApiModelProperty(value = "薪资范围", notes = "30k")
    private String salaryEnd;
    @ApiModelProperty(value = "工作地点", notes = "上海")
    private String location;
    @ApiModelProperty(value = "职位描述", notes = "xxxxxxxxxxxx")
    private String description;
    @ApiModelProperty(value = "招聘状态", notes = "true/false")
    private boolean snap;


    public static JobRequest fromDO(JobDO entity){
        return JobRequest.builder()
                .display(entity.getDisplay())
                .dept(entity.getDept())
                .rank(entity.getRank())
                .category(entity.getCategory())
                .education(entity.getEducation())
                .experience(entity.getExperience())
                .salaryStart(entity.getSalaryStart())
                .salaryEnd(entity.getSalaryEnd())
                .location(entity.getLocation())
                .description(entity.getDescription())
                .snap(entity.getSnap() == ELive.ENABLE.getCode())
                .build();
    }
}
