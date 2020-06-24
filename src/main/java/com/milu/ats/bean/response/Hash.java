package com.milu.ats.bean.response;

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
@ApiModel(value = "通用响应实体", description = "通用响应实体")
public class Hash {
    @ApiModelProperty(value = "key（唯一标识）", notes = "xxxxxx")
    private String key;
    @ApiModelProperty(value = "value值", notes = "xxxxxxx")
    private String value;
}
