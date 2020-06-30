package com.milu.ats.bean.response;

import com.milu.ats.bean.enums.ELive;
import com.milu.ats.bean.request.SetRequest;
import com.milu.ats.dal.entity.SetDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @author max.chen
 * @class
 */
@Data
@NoArgsConstructor
@ApiModel(value = "Set响应实体", description = "返回set集合列表")
public class SetResponse extends SetRequest {
    @ApiModelProperty(value = "setID", notes = "10001")
    private Integer id;
    @ApiModelProperty(value = "是否启用", notes = "true/false")
    private boolean live;

    public static SetResponse fromDO(SetDO entity){
        if(entity == null){
            return null;
        }

        SetRequest request = SetRequest.builder()
                        .type(entity.getType())
                        .display(entity.getDisplay())
                        .code(entity.getCode())
                        .description(entity.getDescription())
                        .build();
        SetResponse response = new SetResponse();

        BeanUtils.copyProperties(request, response);
        response.setId(entity.getId());
        response.setLive(entity.getLive() == ELive.ENABLE.getCode());
        return response;
    }
}
