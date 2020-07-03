package com.milu.ats.bean.enums;

import java.util.Arrays;
import java.util.List;

/**
 * 职位负责人
 * @author max.chen
 * @class
 */
public enum EPosition implements IEBase<EPosition> {
    Recruiter(12, "招聘负责人"),
    Businesser(13, "业务负责人"),
    Interviewer(14, "面试官"),
    Assistant(18, "招聘协助人"),
    ;
    int code;
    String name;

    EPosition(int code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 校验roles中是否包含任意一个pops
     *
     * @param roles
     * @param pops
     */
    public static boolean anyMatch(List<EPosition> roles, EPosition[] pops) {
        return roles.isEmpty() || roles.stream().anyMatch(x -> Arrays.asList(pops).contains(x));
    }
}
