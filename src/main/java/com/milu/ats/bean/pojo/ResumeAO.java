package com.milu.ats.bean.pojo;

import com.milu.ats.dal.entity.ResumeDO;
import com.milu.ats.util.Tools;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author max.chen
 * @class
 */
@Data
@AllArgsConstructor
public class ResumeAO {
    @ApiModelProperty(value = "候选人名称", notes = "张三丰")
    private String name;
    @ApiModelProperty(value = "候选人名称手机号", notes = "18888888888")
    private String mobile;
    @ApiModelProperty(value = "候选人性别", notes = "男/女")
    private String sex;
    @ApiModelProperty(value = "候选人邮箱", notes = "xxx@xxx.com")
    private String email;
    @ApiModelProperty(value = "候选人出生日期-时间戳", notes = "1560000000001")
    private long birthday;
    @ApiModelProperty(value = "候选人所在城市", notes = "上海")
    private String city;

    public static ResumeAO fromDO(ResumeDO entity){
        return entity == null ? null : new ResumeAO(
                entity.getName(), entity.getMobile(), entity.getSex(), entity.getEmail(), Tools.dateToTime(entity.getBirthday()),
                entity.getCity()
        );
    }
}
