package com.milu.ats.bean.response;

import com.milu.ats.bean.request.SetRequest;
import com.milu.ats.dal.entity.SetDO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author max.chen
 * @class
 */
@Data
@NoArgsConstructor
@ApiModel(value = "Set响应实体", description = "返回set集合列表")
public class SetResponse extends SetRequest {
    public static SetResponse fromDO(SetDO entity){
        return entity == null ? null :
                (SetResponse)SetRequest.builder()
                        .id(entity.getId())
                        .type(entity.getType())
                        .code(entity.getCode())
                        .description(entity.getDescription())
                        .build();

    }
}
