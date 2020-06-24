package com.milu.ats.aspect;

import com.milu.ats.annotation.AAuth;
import com.milu.ats.annotation.AHugh;
import com.milu.ats.bean.enums.ERole;
import com.milu.ats.bean.pojo.EMessage;
import com.milu.ats.bean.pojo.Employee;
import com.milu.ats.config.AtsException;
import com.milu.ats.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author max.chen
 * @class
 */
public class ResolveParamInterceptor extends HandlerInterceptorAdapter implements HandlerMethodArgumentResolver {

    @Autowired
    IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //如果对象不是handlermethod直接返回
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        //  方法是否需要验证身份
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        boolean hasNeed = false;


        // 根据方法注入@NeedAuth验证
        Method method = handlerMethod.getMethod();
        // 是否需要校验auth验证
        AAuth auth = method.getAnnotation(AAuth.class);
        AAuth classNeedAuth = handlerMethod.getBeanType().getAnnotation(AAuth.class);
        hasNeed = (auth != null || classNeedAuth != null);

        //  如果需要验证身份
        if (hasNeed) {
            ERole[] roles = auth != null ? auth.roles() : classNeedAuth.roles();
            Employee e = userService.getUserFromRequest(request);
            // 需要的角色不为空，而且不囊括在当前有用户的激活角色
            if (roles.length > 0 && ERole.anyMatch(Arrays.asList(roles), new ERole[]{e.getActive()})) {
                Assert.isTrue(false, EMessage.AUTH_NO.show());
            }
        }

        return super.preHandle(request, response, handler);
    }


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AHugh.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Employee e = userService.getUserFromRequest(request);

        if(e == null){
            throw new AtsException(401, "登录未验证");
        }
        return e;
    }
}
