package com.milu.ats.bean.response;

import com.milu.ats.bean.enums.ESet;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author max.chen
 * @class
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "SetType响应实体", description = "set type 响应")
public class SetTypeResponse extends Hash{

    @ApiModelProperty(value = "type显示中文名称", notes = "地点")
    private String display;

    public static SetTypeResponse fromEnum(ESet eSet){
        SetTypeResponse response = (SetTypeResponse)Hash.builder()
                .key(String.valueOf(eSet.getType()))
                .value(eSet.getCode())
                .build();
        response.setDisplay(eSet.getDisplay());
        return response;
    }
}
