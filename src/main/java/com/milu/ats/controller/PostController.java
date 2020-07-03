package com.milu.ats.controller;

import com.milu.ats.annotation.AHugh;
import com.milu.ats.bean.ReturnVO;
import com.milu.ats.bean.pojo.Employee;
import com.milu.ats.bean.request.ResumeSearchRequest;
import com.milu.ats.bean.response.PageResponse;
import com.milu.ats.bean.response.ResumeSearchResponse;
import com.milu.ats.service.IResumeService;
import com.milu.ats.util.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author max.chen
 * @class
 */
@Slf4j
@RestController
@RequestMapping("resume")
@Api(tags = "简历投递", description = "简历：投递、评价、流转")
public class PostController {
    @Autowired
    IResumeService resumeService;
    /********************投递阶段**********************/

    @PutMapping("/post/{jobID}/{aID}")
    @ApiOperation(value = "简历投递", notes = "上传附件后，投递该职位简历")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="path", name = "jobID", value = "已存在职位的ID", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="path", name = "aID", value = "附件ID", required = true, dataType = "String")
    })
    public ReturnVO postFotJob(@PathVariable("jobID") String jobID, @PathVariable("aID") String aID) {
        resumeService.post(Tools.idDecode(jobID), Tools.idDecode(aID));
        return ReturnVO.success();
    }

    @PostMapping("/post/list/{jobID}")
    @ApiOperation(value = "职位投递简历列表", notes = "查看职位投递的所有简历")
    @ApiImplicitParam(paramType="path", name = "jobID", value = "已存在职位的ID", required = true, dataType = "String")
    public ReturnVO<PageResponse<ResumeSearchResponse>> listPostInJob(@AHugh Employee e, @PathVariable("jobID") String jobID, @RequestBody ResumeSearchRequest request) {
        request.setJobId(jobID);
        return ReturnVO.success(resumeService.postResumeSearch(e, request));
    }

    @GetMapping("/post/total/{jobID}")
    @ApiOperation(value = "职位投递简历每个阶段数量统计", notes = "职位投递简历每个阶段数量统计")
    @ApiImplicitParam(paramType="path", name = "jobID", value = "已存在职位的ID", required = true, dataType = "String")
    public ReturnVO<Map<Integer, Integer>> totalPostInJob(@AHugh Employee e, @PathVariable("jobID") String jobID) {
        int jobId = Tools.idDecode(jobID);
        Map<Integer, Long> map = jobId > 0 ? resumeService.totalPostInJob(e, jobId): new HashMap<>();
        return ReturnVO.success(map);
    }
}
