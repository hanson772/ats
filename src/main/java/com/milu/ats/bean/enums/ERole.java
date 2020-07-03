package com.milu.ats.bean.enums;

import java.util.Arrays;
import java.util.List;

/**
 * 角色
 * @author max.chen
 * @class
 */
public enum ERole implements IEBase<ERole> {
    Manager(10, "管理员"),
    Recruiter(12, "招聘官"),
    Interviewer(14, "面试官"),
    Assistant(18, "招聘协助人"),
    Unknow(0, "未知角色"),
    ;
    int code;
    String name;

    ERole(int code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 校验roles中是否包含任意一个pops
     *
     * @param roles
     * @param pops
     */
    public static boolean anyMatch(List<ERole> roles, ERole[] pops) {
        return roles.isEmpty() || roles.stream().anyMatch(x -> Arrays.asList(pops).contains(x));
    }
}
