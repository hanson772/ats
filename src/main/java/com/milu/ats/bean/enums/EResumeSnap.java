package com.milu.ats.bean.enums;

/**
 * @author max.chen
 * @class
 */
public enum EResumeSnap implements IEBase<ERole>  {
    Audition(11, "已投递"),
    Junior(11, "初选阶段"),
    Senior(11, "用人部门筛选"),
    Interview(11, "面试阶段"),
    Offer(11, "Offer阶段"),
    Unknow(0, "未知"),
    ;


    int code;
    String name;

    EResumeSnap(int code, String name) {
        this.code = code;
        this.name = name;
    }

}
