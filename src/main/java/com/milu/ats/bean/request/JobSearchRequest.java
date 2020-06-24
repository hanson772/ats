package com.milu.ats.bean.request;

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
@ApiModel(value = "职位搜索请求实体", description = "搜索职位")
public class JobSearchRequest extends PageBase {
    @ApiModelProperty(value = "职位", notes = "高级产品经理")
    private String display;
    @ApiModelProperty(value = "部门", notes = "技术研究中心")
    private String dept;
    @ApiModelProperty(value = "岗位类别", notes = "111")
    private Integer category;
}
