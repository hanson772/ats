package com.milu.ats.bean.pojo;

/**
 * @author max.chen
 * @class
 */
public enum EMessage {
    OBJECT_NOT_EXIST(1000, "数据不存在"),

    JOB_NOT_EXIST(1001, "职位不存在"),


    /**基础数据Set**/
    SET_NOT_EXIST(1031, "基础数据不存在"),

    /**附件校验**/
    ATTACH_NOT_EXIST(1041, "文件不存在"),

    /**权限校验**/
    AUTH_NO(1091, "您没有权限执行该操作"),

    PARAM_ERROR(1121, "参数不正确"),
    ;

    int code;
    String msg;
    EMessage(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String show(){
        return this.msg;
    }
}
