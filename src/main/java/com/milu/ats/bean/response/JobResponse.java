package com.milu.ats.bean.response;

import com.milu.ats.bean.request.JobChannelRequest;
import com.milu.ats.bean.request.JobPersonnelRequest;
import com.milu.ats.bean.request.JobRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author max.chen
 * @class
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "职位响应实体", description = "职位详情")
public class JobResponse {
    @ApiModelProperty(value = "id", notes = "1asd123as3324321")
    private String id;
    @ApiModelProperty(value = "职位详情", notes = "xxxx")
    private JobRequest body;
    @ApiModelProperty(value = "负责人员信息", notes = "xxxx")
    private JobPersonnelRequest personnel;
    @ApiModelProperty(value = "渠道相关信息", notes = "xxxx")
    private List<JobChannelRequest> channel;

}
