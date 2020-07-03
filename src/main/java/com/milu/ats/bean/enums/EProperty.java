package com.milu.ats.bean.enums;

import com.milu.ats.config.SpringContextUtil;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

/**
 * 属性配置
 * @author max.chen
 * @class
 */
public enum EProperty {
    PROJECT_ID_ENCODE("project.id.encode"),
    PROJECT_ID_PRE("project.id.pre"),

    UNKNOWN("unknown");

    String name;

    public String value() {
        return readProperty(this.name);
    }

    public String value(String dvalue) {
        return readProperty(this.name, dvalue);
    }


    public String code(){
        return this.name;
    }

    EProperty(String name) {
        this.name = name;
    }

    public static String readProperty(String key, String defaultvalue) {
        String value = readProperty(key);
        return value = StringUtils.hasText(value) ?  value : defaultvalue;
    }

    public static String readProperty(String key) {
        Environment environment = SpringContextUtil.getApplicationContext().getBean(Environment.class);
        return environment.getProperty(key);
    }
}
