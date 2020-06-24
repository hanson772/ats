package com.milu.ats.bean.response;

import com.milu.ats.bean.request.PageBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author max.chen
 * @class
 */
@Data
@ApiModel(value = "分页搜索返回")
public class PageResponse<T> extends PageBase {
    @ApiModelProperty(value = "总数")
    private Integer count;
    @ApiModelProperty(value = "结果list")
    List<T> list;
}

