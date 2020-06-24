package com.milu.ats.bean.enums;

/**
 * @author max.chen
 * @class
 */
public enum ELive implements IEBase<ELive> {
    ENABLE(1, "启用"),
    DISABLE(0, "停用");

    int code;
    String name;

    ELive(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
