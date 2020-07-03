package com.milu.ats.bean.response;

import com.milu.ats.dal.entity.JobDO;
import com.milu.ats.util.Tools;
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
@ApiModel(value = "Job职位搜索响应实体", description = "搜索职位")
public class JobSearchResponse {
    @ApiModelProperty(value = "id", notes = "1asd123as3324321")
    private String id;
    @ApiModelProperty(value = "职位", notes = "高级产品经理")
    private String display;
    @ApiModelProperty(value = "候选人数量", notes = "100")
    private Integer candidateQuantity;
    @ApiModelProperty(value = "招聘负责人", notes = "张无忌,张三丰")
    private String recruiter;
    @ApiModelProperty(value = "业务负责人", notes = "张无忌,张三丰")
    private String businesser;
    @ApiModelProperty(value = "招聘协助人", notes = "张无忌,张三丰")
    private String assistant;

    @ApiModelProperty(value = "招聘状态", notes = "true:招聘中； false: 关闭")
    private Boolean snap;
    @ApiModelProperty(value = "职位创建时间", notes = "1592878147")
    private long created;
    @ApiModelProperty(value = "职位关闭时间", notes = "1592878147")
    private long closed;

    public static JobSearchResponse fromDO(JobDO entity){
        return JobSearchResponse.builder()
                .id(Tools.idEncode(entity.getId()))
                .display(entity.getDisplay())
                .snap(entity.getSnap())
                .created(Tools.dateToTime(entity.getCreated()))
                .closed(Tools.dateToTime(entity.getClosed()))
                .build();
    }
}