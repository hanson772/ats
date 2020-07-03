package com.milu.ats.service;

import com.milu.ats.bean.pojo.Employee;
import com.milu.ats.bean.request.ResumeSearchRequest;
import com.milu.ats.bean.response.PageResponse;
import com.milu.ats.bean.response.ResumeResponse;
import com.milu.ats.bean.response.ResumeSearchResponse;

import java.util.Map;

/**
 * @author max.chen
 * @class
 */
public interface IResumeService {
    void post(int jobId, int aId);

    PageResponse<ResumeSearchResponse> postResumeSearch(Employee e, ResumeSearchRequest request);

    Map<Integer, Long> totalPostInJob(Employee e, int postId);

    ResumeResponse detail(int resumeId);
}
