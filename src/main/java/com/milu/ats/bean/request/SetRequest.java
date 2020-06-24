package com.milu.ats.bean.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.sql.Insert;
import org.hibernate.sql.Update;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author max.chen
 * @class
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Set请求实体", description = "保存，编辑set值")
public class SetRequest {
    @ApiModelProperty(value = "setID", notes = "10001")
    private Integer id;
    @NotBlank(message = "请填写set type 类型值,如：101", groups = {Insert.class})
    @ApiModelProperty(value = "set type类型ID", notes = "101")
    private Integer type;
    @NotBlank(message = "请填写数据code", groups = {Insert.class})
    @Length(max = 100, message = "数据code最多支持输入100个字符", groups = {Insert.class})
    @ApiModelProperty(value = "set 编码", notes = "SHANGHAI")
    private String code;
    @NotBlank(message = "请填写数据显示名称", groups = {Insert.class, Update.class})
    @Length(max = 100, message = "数据显示名称最多支持输入100个字符", groups = {Insert.class, Update.class})
    @ApiModelProperty(value = "set 显示名称", notes = "上海")
    private String dispaly;
    @ApiModelProperty(value = "set 描述", notes = "xxxxxxxx")
    private String description;
}