package com.milu.ats.bean.request;

import com.milu.ats.bean.pojo.PageBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author max.chen
 * @class
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "简历搜索请求实体", description = "搜索简历")
public class ResumeSearchRequest extends PageBase {
    @ApiModelProperty(value = "投递职位ID", notes = "1111111111111")
    private String jobId;
    @ApiModelProperty(value = "关键字查询： 候选人姓名、手机号、邮箱、公司、职位、学校", notes = "张无忌")
    private String key;

    @ApiModelProperty(value = "公司名称", notes = "中国电力")
    private String company;
    @ApiModelProperty(value = "工作年限查找", notes = "ESet:type = 106")
    private String seniority;
    @ApiModelProperty(value = "职位", notes = "工程师")
    private String job;
    @ApiModelProperty(value = "学校", notes = "学校")
    private String college;
    @ApiModelProperty(value = "学历", notes = "ESet:type = 105")
    private String degree;
    @ApiModelProperty(value = "渠道", notes = "EFrom:")
    private String from;

    @ApiModelProperty(value = "投递状态", notes = "21：初筛，22：用人部门筛选，23：面试中，24：Offer中，25：入职中，26: 淘汰")
    private Integer state;


}
