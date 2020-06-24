package com.milu.ats.service;

import com.milu.ats.bean.pojo.Employee;
import com.milu.ats.bean.request.SetRequest;
import com.milu.ats.bean.response.SetResponse;
import com.milu.ats.bean.response.SetTypeResponse;

import java.util.List;

/**
 * @author max.chen
 * @class
 */
public interface ISetService {
    List<SetTypeResponse> allSetTypes();

    List<SetResponse> allSetsByType(boolean isAll, int type);

    void save(Employee e, SetRequest request);

    void update(Employee e, int sid, SetRequest request);

    void switcher(Employee e, int sid, boolean isLive);
}
