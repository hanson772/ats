package com.milu.ats.bean.request;

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
@ApiModel(value = "多ID集合请求", description = "多ID集合请求")
public class IdRequest {
    @ApiModelProperty(value = "多ID集合", notes = "[12309cuj89ue12,129389dwasdo]")
    private List<String> ids;
}
