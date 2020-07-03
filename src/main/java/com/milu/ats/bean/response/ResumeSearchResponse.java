package com.milu.ats.bean.response;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.milu.ats.bean.pojo.ResumeEducationAO;
import com.milu.ats.bean.pojo.ResumeExperienceAO;
import com.milu.ats.dal.entity.vo.ResumeSearchVO;
import com.milu.ats.util.Tools;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author max.chen
 * @class
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "简历搜索响应实体", description = "简历")
public class ResumeSearchResponse {
    @ApiModelProperty(value = "投递记录id（不是简历ID）", notes = "1asd123as3324321")
    private String postId;
    @ApiModelProperty(value = "候选人姓名", notes = "张无忌")
    private String display;
    @ApiModelProperty(value = "候选人生日时间戳", notes = "1561323123")
    private long birthday;
    @ApiModelProperty(value = "候选人手机号码", notes = "13888888888")
    private String mobile;
    @ApiModelProperty(value = "候选人性别", notes = "男")
    private String sex;
    @ApiModelProperty(value = "候选人邮箱", notes = "xxx@yyy.com")
    private String email;
    @ApiModelProperty(value = "候选人所在城市", notes = "上海")
    private String city;
    @ApiModelProperty(value = "候选人最高学历", notes = "ESet: type=105")
    private String degree;
    @ApiModelProperty(value = "候选人工作年前", notes = "ESet: type=106")
    private String seniority;
    @ApiModelProperty(value = "投递渠道", notes = "101：社招，102：校招，103：内推，104：第三方渠道，105：自己投递，106：招聘会")
    private Integer from;
    @ApiModelProperty(value = "候选人投递时间", notes = "1561323123")
    private long created;
    @ApiModelProperty(value = "候选人投递状态", notes = "11:新申请,12:评审中,13:面试中,14:Offer准备,15:Offer已接受,16:待入职,15:已入职,16:拒绝入职,20:淘汰")
    private Integer snap;
    @ApiModelProperty(value = "工作经历", notes = "[]")
    List<ResumeExperienceAO> experiences;
    @ApiModelProperty(value = "教育经历", notes = "[]")
    List<ResumeEducationAO> educations;

    public static ResumeSearchResponse fromVO(ResumeSearchVO entity){
        ResumeSearchResponse response = ResumeSearchResponse.builder()
                .postId(Tools.idEncode(entity.getResumeId()))
                .display(entity.getName())
                .mobile(entity.getMobile())
                .sex(entity.getSex())
                .birthday(Tools.dateToTime(entity.getBirthday()))
                .city(entity.getCity())
                .snap(entity.getSnap())
                .seniority(entity.getSeniority())
                .from(entity.getFrom())
                .created(Tools.dateToTime(entity.getCreated()))
                .build();
        if(StringUtils.hasText(entity.getExperiences())){
            try {
                response.setExperiences(JSONObject.parseArray(entity.getExperiences(), ResumeExperienceAO.class));
            }catch (Exception ex){}
        }
        if(StringUtils.hasText(entity.getEducations())){
            try {
                response.setEducations(JSONObject.parseArray(entity.getEducations(), ResumeEducationAO.class));
            }catch (Exception ex){}
        }
        return response;
    }
}