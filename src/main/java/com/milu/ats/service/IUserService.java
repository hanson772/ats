package com.milu.ats.service;

import com.milu.ats.bean.pojo.Employee;

import javax.servlet.http.HttpServletRequest;

/**
 * @author max.chen
 * @class
 */
public interface IUserService {
    Employee getUserFromRequest(HttpServletRequest request);
}
