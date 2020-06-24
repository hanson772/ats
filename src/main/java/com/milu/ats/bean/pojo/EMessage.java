package com.milu.ats.bean.pojo;

/**
 * @author max.chen
 * @class
 */
public enum EMessage {
    JOB_NOT_EXIST(1001, "职位不存在"),


    /**基础数据Set**/
    SET_NOT_EXIST(1031, "基础数据不存在"),


    /**权限校验**/
    AUTH_NO(1091, "您没有权限执行该操作"),
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
