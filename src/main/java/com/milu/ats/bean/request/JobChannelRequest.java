package com.milu.ats.bean.request;

import com.milu.ats.bean.enums.ELive;
import com.milu.ats.dal.entity.JobChannelDO;
import com.milu.ats.util.Tools;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.sql.Insert;

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
public class JobChannelRequest {
    private String id;
    @NotBlank(message = "请选择渠道", groups = {Insert.class})
    @ApiModelProperty(value = "渠道ID", notes = "11")
    private int channelId;
    @NotBlank(message = "请填写年龄要求", groups = {Insert.class})
    @ApiModelProperty(value = "年龄要求", notes = "35")
    private int age;
    @NotBlank(message = "请填写福利", groups = {Insert.class})
    @ApiModelProperty(value = "福利", notes = "班车")
    private String welfare;
    @ApiModelProperty(value = "是否开启发布", notes = "true/false")
    private boolean snap;

    public static JobChannelRequest fromDO(JobChannelDO entity){
        return JobChannelRequest.builder()
                .id(Tools.idEncode(entity.getId()))
                .channelId(entity.getChannelId())
                .age(entity.getAge())
                .welfare(entity.getWelfare())
                .snap(entity.getSnap() == ELive.ENABLE.getCode())
                .build();
    }
}
