package com.milu.ats.annotation;

import com.milu.ats.bean.enums.ERole;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * @author max.chen
 * @class
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface AAuth{
    ERole[] roles() default {ERole.Unknow}; // 默认只读用户
}

