package com.milu.ats.service;

import com.milu.ats.bean.pojo.Employee;
import com.milu.ats.bean.request.JobChannelRequest;
import com.milu.ats.bean.request.JobPersonnelRequest;
import com.milu.ats.bean.request.JobRequest;
import com.milu.ats.bean.request.JobSearchRequest;
import com.milu.ats.bean.response.JobResponse;
import com.milu.ats.bean.response.JobSearchResponse;
import com.milu.ats.bean.response.PageResponse;

/**
 * @author max.chen
 * @class
 */
public interface IJobService {
    PageResponse<JobSearchResponse> searchByEmployee(Employee e, JobSearchRequest request);

    void saveJob(Employee e, int jobId, JobRequest request);

    void savePersonnel(Employee e, int jobId, JobPersonnelRequest request);

    void saveChannel(Employee e, int jobId, JobChannelRequest request);

    void change(Employee e, int jobId, boolean isOpen);

    JobResponse detail(Employee e, int jobId, boolean isAll);
}
