package com.milu.ats.bean.pojo;

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
@ApiModel(value = "简单对象实体", description = "简单对象实体")
public class Easy {
    @ApiModelProperty(value = "实体key-唯一标识", notes = "xxxxx")
    private String key;
    @ApiModelProperty(value = "实体value", notes = "xxxxx")
    private String value;

    public static Easy get(String key, String value){
        return Easy.builder().key(key).value(value).build();
    }
}
