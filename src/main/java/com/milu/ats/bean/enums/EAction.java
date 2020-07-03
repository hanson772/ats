package com.milu.ats.bean.enums;

/**
 * 简历来源
 * @author max.chen
 * @class
 */
public enum EAction implements IEBase<EAction>{
    Pass(81,  "通过"),
    Out(82, "淘汰"),
    Join(83, "加入职位"),
    Trans(84, "转发评审"),
    Rollback(85, "回复原状态"),
    ;

    int code;
    String name;

    EAction(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public void checkActionInState(EAction action, EPostState state){

    }
}
