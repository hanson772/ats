package com.milu.ats.bean.enums;

/**
 * 基础数据
 * @author max.chen
 * @class
 */
public enum ESet {
    Location(101, "location", "地点"),
    Category(102, "jobCategory", "岗位类别"),
    Rank(103, "jobRank", "职级范围"),
    Degree(105, "Degree", "学历要求"),
    Seniority(106, "seniority", "工作年限"),
    ;

    int type;
    String code;
    String display;

    ESet(int type, String code, String display) {
        this.type = type;
        this.code = code;
        this.display = display;
    }

    public int getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public String getDisplay() {
        return display;
    }
}
