package com.milu.ats.bean.response;

import com.milu.ats.bean.pojo.ResumeAO;
import com.milu.ats.bean.pojo.ResumeEducationAO;
import com.milu.ats.bean.pojo.ResumeExperienceAO;
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
@ApiModel(value = "简历响应实体", description = "简历")
public class ResumeResponse {
    @ApiModelProperty(value = "简历ID", notes = "1239xasu89d1289e")
    String id;
    @ApiModelProperty(value = "简历主题信息", notes = "")
    ResumeAO body;
    @ApiModelProperty(value = "工作经历列表", notes = "")
    List<ResumeExperienceAO> experiences;
    @ApiModelProperty(value = "教育经历列表", notes = "")
    List<ResumeEducationAO> educations;
}
