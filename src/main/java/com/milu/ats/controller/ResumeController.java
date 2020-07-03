package com.milu.ats.controller;

import com.milu.ats.annotation.AHugh;
import com.milu.ats.bean.ReturnVO;
import com.milu.ats.bean.enums.EAction;
import com.milu.ats.bean.pojo.EMessage;
import com.milu.ats.bean.pojo.Employee;
import com.milu.ats.bean.request.IdRequest;
import com.milu.ats.service.IResumeService;
import com.milu.ats.util.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author max.chen
 * @class
 */
@Slf4j
@RestController
@RequestMapping("resume")
@Api(tags = "简历", description = "简历：查询、投递、保存、编辑接口")
public class ResumeController {
    @Autowired
    IResumeService resumeService;

    @GetMapping("/detail/{resumeID}")
    @ApiOperation(value = "查看简历", notes = "查看简历详情")
    @ApiImplicitParam(paramType="path", name = "resumeID", value = "简历的ID", required = true, dataType = "String")
    public ReturnVO<Map<Integer, Integer>> totalPostInJob(@AHugh Employee e, @PathVariable("resumeID") String resumeID) {
        return ReturnVO.success(resumeService.detail(Tools.idDecode(resumeID)));
    }

    @GetMapping("/action/{act}")
    @ApiOperation(value = "审阅简历", notes = "审阅简历，进入下一个环节， Request:ids: postID投递简历ID")
    @ApiImplicitParam(paramType="path", name = "act", value = "审阅简历进入下一环节：81：通过，82：淘汰，83：加入职位，84：转发评审，85：恢复原状态", required = true, dataType = "Integer")
    public ReturnVO<Map<Integer, Integer>> actionPost(@AHugh Employee e, @PathVariable("act") Integer act, @RequestBody IdRequest request) {
        List<Integer> ids = Tools.idDecodeBatch(request.getIds());
        EAction action = EAction.Pass.getByCode(act);
        Assert.notNull(action, EMessage.PARAM_ERROR.show());
        //resumeService.action(e, action, ids);
        return ReturnVO.success();
    }
}
