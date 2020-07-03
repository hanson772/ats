package com.milu.ats.bean.enums;

import org.apache.tomcat.util.digester.ArrayStack;

import javax.persistence.criteria.CriteriaBuilder;
import java.nio.channels.NonWritableChannelException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 简历投递查询状态
 * @author max.chen
 * @class
 */
public enum EPostState implements IEBase<EPostState>  {
    FirstFilter(21, "初筛：新申请"),
    BizFilter(22, "用人部门筛选：评审中"),
    Interviewing(23, "面试中：面试中"),
    Offering(24, "Offer中：Offer中状态（包含从Offer准备~候选人接受Offer）"),
    OnBoarding(25, "入职中：入职中状态（包含待入职、已入职、拒绝入职状态）"),
    Out(26, "已淘汰"),
    Unknow(0, "未知")
    ;


    int code;
    String name;

    EPostState(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public List<Integer> selectSnap(){
        List<Integer> snpas = new ArrayList<>();
        switch (this){
            case FirstFilter:
                snpas = Arrays.asList(EPostSnap.Audition.getCode());
                break;
            case BizFilter:
                snpas = Arrays.asList(EPostSnap.Approving.getCode());
                break;
            case Interviewing:
                snpas = Arrays.asList(EPostSnap.Interviewing.getCode());
                break;
            case Offering:
                snpas = Arrays.asList(EPostSnap.Offering.getCode(), EPostSnap.Offered.getCode());
                break;
            case OnBoarding:
                snpas = Arrays.asList(EPostSnap.PreBoarding.getCode(),EPostSnap.OnBoarding.getCode(),EPostSnap.RejectBoarding.getCode());
                break;
            case Out:
                snpas = Arrays.asList(EPostSnap.Out.getCode());
                break;
        }
        return new ArrayList<>();
    }
}
