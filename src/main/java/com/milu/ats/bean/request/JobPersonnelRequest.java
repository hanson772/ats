package com.milu.ats.bean.request;

import com.milu.ats.bean.pojo.Easy;
import com.milu.ats.bean.valid.Insert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author max.chen
 * @class
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "职位保存请求实体", description = "保存职位")
public class JobPersonnelRequest {
    @NotNull(message = "请填写招聘负责人", groups = {Insert.class})
    @ApiModelProperty(value = "招聘负责人", notes = "张无忌")
    private List<Easy> recruiter;
    @NotNull(message = "请填写业务负责人", groups = {Insert.class})
    @ApiModelProperty(value = "业务负责人", notes = "殷天正")
    private List<Easy> businesser;
    @NotNull(message = "请填写面试官", groups = {Insert.class})
    @ApiModelProperty(value = "面试官", notes = "韦一笑")
    private List<Easy> interviewer;
    @NotNull(message = "请填写面试协助人", groups = {Insert.class})
    @ApiModelProperty(value = "协助人", notes = "五行旗")
    private List<Easy> assistant;

}
