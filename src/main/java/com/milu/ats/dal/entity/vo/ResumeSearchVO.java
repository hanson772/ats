package com.milu.ats.dal.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author max.chen
 * @class
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeSearchVO {
    Integer jobId;
    Integer resumeId;
    String name;
    String mobile;
    String sex;
    String email;
    Date birthday;
    String city;
    String degree;
    String seniority;
    Integer from;
    Integer snap;
    Date created;
    String experiences;
    String educations;
}
