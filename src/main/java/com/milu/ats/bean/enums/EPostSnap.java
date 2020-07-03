package com.milu.ats.bean.enums;


/**
 * 简历投递状态
 * @author max.chen
 * @class
 */
public enum EPostSnap implements IEBase<EPostSnap>  {
    Audition(11, "新申请"),
    Approving(12, "评审中"),
    Interviewing(13, "面试中"),
    Offering(14, "Offer准备"),
    Offered(15, "Offer已接受"),
    PreBoarding(16, "待入职"),
    OnBoarding(17, "已入职"),
    RejectBoarding(18, "拒绝入职"),
    Out(20, "淘汰"),
    Unknow(0, "未知"),

    ;


    int code;
    String name;

    EPostSnap(int code, String name) {
        this.code = code;
        this.name = name;
    }

    // Eaction = Pass时，返回下一阶段
    public EPostSnap next(){
        EPostSnap next = null;
        switch (this){
            case Audition:
                next = Approving;
                break;
            case Approving:
                next = Interviewing;
                break;
            case Interviewing:
                next = Offering;
                break;
            case Offering:
                next = Offered;
                break;
            case Offered:
                next = PreBoarding;
                break;
            case PreBoarding:
                next = OnBoarding;
                break;
            default:
                break;
        }
        return next;
    }

}
