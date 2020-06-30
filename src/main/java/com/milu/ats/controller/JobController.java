package com.milu.ats.controller;

import com.milu.ats.annotation.AAuth;
import com.milu.ats.annotation.AHugh;
import com.milu.ats.bean.ReturnVO;
import com.milu.ats.bean.enums.ERole;
import com.milu.ats.bean.pojo.Employee;
import com.milu.ats.bean.request.*;
import com.milu.ats.bean.response.*;
import com.milu.ats.service.IJobService;
import com.milu.ats.service.ISetService;
import com.milu.ats.util.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Insert;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.UsesSunHttpServer;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.ServletSecurity;
import java.util.List;

/**
 * @author max.chen
 * @class
 */
@Slf4j
@RestController
@RequestMapping("job")
@Api(tags = "职位", description = "职位：查询、保存、编辑接口")
public class JobController {
    @Autowired
    IJobService jobService;

    @PostMapping("/search")
    @ApiOperation(value = "职位搜索", notes = "仅内部Employee查询职位")
    public ReturnVO<PageResponse<JobSearchResponse>> search(@AHugh Employee e, @RequestBody JobSearchRequest request) {
        return ReturnVO.success(jobService.searchByEmployee(e, request));
    }

    @PostMapping("/save/job/{jobID}")

    @ApiOperation(value = "保存职位-主信息", notes = "保存、编辑职位基础信息")
    @ApiImplicitParam(paramType="path", name = "jobID", value = "0：新建职位，其他：编辑已存在职位的ID", required = true, dataType = "String")
    public ReturnVO saveJob(@AHugh Employee e, @PathVariable("jobID") String jobID, @RequestBody JobRequest request) {
        int jobId = Tools.idDecode(jobID);
        jobService.saveJob(e, jobId, request);
        return ReturnVO.success();
    }

    @PostMapping("/save/personnel/{jobID}")
    @ApiOperation(value = "保存职位-负责人", notes = "保存、编辑职位-相关负责人信息")
    @ApiImplicitParam(paramType="path", name = "jobID", value = "已存在职位的ID", required = true, dataType = "String")
    public ReturnVO savePersonnel(@AHugh Employee e, @PathVariable("jobID") String jobID, @RequestBody JobPersonnelRequest request) {
        int jobId = Tools.idDecode(jobID);
        jobService.savePersonnel(e, jobId, request);
        return ReturnVO.success();
    }

    @PostMapping("/save/channel/{jobID}")
    @ApiOperation(value = "保存职位-渠道信息", notes = "保存、编辑职位-渠道信息")
    @ApiImplicitParam(paramType="path", name = "jobID", value = "已存在职位的ID", required = true, dataType = "String")
    public ReturnVO saveChannel(@AHugh Employee e, @PathVariable("jobID") String jobID, @RequestBody JobChannelRequest request) {
        int jobId = Tools.idDecode(jobID);
        jobService.saveChannel(e, jobId, request);
        return ReturnVO.success();
    }

    @PutMapping("/open/{jobID}")
    @ApiOperation(value = "放开职位", notes = "放开职位")
    @ApiImplicitParam(paramType="path", name = "jobID", value = "已存在职位的ID", required = true, dataType = "String")
    public ReturnVO openJob(@AHugh Employee e, @PathVariable("jobID") String jobID) {
        int jobId = Tools.idDecode(jobID);
        jobService.change(e, jobId, true);
        return ReturnVO.success();
    }

    @PutMapping("/close/{jobID}")
    @ApiOperation(value = "关闭职位", notes = "关闭职位")
    @ApiImplicitParam(paramType="path", name = "jobID", value = "已存在职位的ID", required = true, dataType = "String")
    public ReturnVO closeJob(@AHugh Employee e, @PathVariable("jobID") String jobID) {
        int jobId = Tools.idDecode(jobID);
        jobService.change(e, jobId, false);
        return ReturnVO.success();
    }


    @GetMapping("/get/{jobID}")
    @ApiOperation(value = "查看职位详细信息-详情", notes = "查看职位详细信息-详情")
    @ApiImplicitParam(paramType="path", name = "jobID", value = "已存在职位的ID", required = true, dataType = "String")
    public ReturnVO<JobResponse> getDetailForJob(@AHugh Employee e, @PathVariable("jobID") String jobID) {
        int jobId = Tools.idDecode(jobID);
        jobService.detail(e, jobId, true);
        return ReturnVO.success();
    }

    @GetMapping("/simple/{jobID}")
    @ApiOperation(value = "查看职位详细信息-简单信息", notes = "查看职位详细信息-简单职位信息，不包含渠道信息、负责人信息")
    @ApiImplicitParam(paramType="path", name = "jobID", value = "已存在职位的ID", required = true, dataType = "String")
    public ReturnVO<JobResponse> getSimpleForJob(@AHugh Employee e, @PathVariable("jobID") String jobID) {
        int jobId = Tools.idDecode(jobID);
        jobService.detail(e, jobId, true);
        return ReturnVO.success();
    }

}
