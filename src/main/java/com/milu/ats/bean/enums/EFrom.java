package com.milu.ats.bean.enums;

/**
 * 简历来源
 * @author max.chen
 * @class
 */
public enum EFrom  implements IEBase<EFrom>{
    Society(101,  "社招"),
    Campus(102, "校招"),
    Introduction(103, "内推"),
    Channel(104, "第三方渠道"),
    Post(105, "自己投递"),
    JobFair(106, "招聘会"),
    ;

    int code;
    String name;

    EFrom(int code,  String name) {
        this.code = code;
        this.name = name;
    }
}
