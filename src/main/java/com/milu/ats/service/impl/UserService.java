package com.milu.ats.service.impl;

import com.milu.ats.bean.enums.ERole;
import com.milu.ats.bean.pojo.Employee;
import com.milu.ats.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author max.chen
 * @class
 */
@Service
@Slf4j
public class UserService implements IUserService {
    @Override
    public Employee getUserFromRequest(HttpServletRequest request) {
        String role = request.getHeader("x-role");
        role = StringUtils.hasText(role)? role : "ass";
        Employee e = null;
        switch (role){
            case "mg":
                e = Employee.builder().name("张无忌").email("wuji.zhang").account("wuji.zhang").active(ERole.Manager).build();
                break;
            case "rec":
                e = Employee.builder().name("殷天正").email("tianzheng.yin").account("tianzheng.yin").active(ERole.Recruiter).build();
                break;
            case "int":
                e = Employee.builder().name("韦一笑").email("yixiao.wei").account("yixiao.wei").active(ERole.Interviewer).build();
                break;
            case "ass":
                e = Employee.builder().name("陈友谅").email("youliang.chen").account("youliang.chen").active(ERole.Assistant).build();
            default:
                break;
        }
        return e;
    }
}
