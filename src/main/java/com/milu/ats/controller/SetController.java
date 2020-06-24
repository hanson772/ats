package com.milu.ats.controller;

import com.milu.ats.annotation.AAuth;
import com.milu.ats.annotation.AHugh;
import com.milu.ats.bean.ReturnVO;
import com.milu.ats.bean.enums.ERole;
import com.milu.ats.bean.pojo.Employee;
import com.milu.ats.bean.request.SetRequest;
import com.milu.ats.bean.response.SetResponse;
import com.milu.ats.bean.response.SetTypeResponse;
import com.milu.ats.service.ISetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Insert;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author max.chen
 * @class
 */
@Slf4j
@RestController
@RequestMapping("set")
@Api(tags = "基础数据", description = "基础数据：查询、保存、编辑接口")
public class SetController {
    @Autowired
    ISetService setService;

    @GetMapping("/types")
    @ApiOperation(value = "查询基础数据类型", notes = "返回所有基础数据类型枚举值列表")
    public ReturnVO<List<SetTypeResponse>> types() {
        List<SetTypeResponse> types = setService.allSetTypes();
        return ReturnVO.success(types);
    }


    @GetMapping("/list/{type}")
    @ApiOperation(value = "查询基础数据列表-仅有效", notes = "返回相关type类型下的所有基础数据值结合(仅有效基础数据)")
    @ApiImplicitParam(paramType="path", name = "type", value = "0：全部，其他：基础数据类型type值", required = true, dataType = "Integer")
    public ReturnVO<List<SetResponse>> list(@PathVariable("type") Integer type) {
        List<SetResponse> types = setService.allSetsByType(false, type);
        return ReturnVO.success(types);
    }

    @GetMapping("/mg/list/{type}")
    @AAuth(roles = {ERole.Manager})
    @ApiOperation(value = "查询基础数据列表-全部", notes = "返回相关type类型下的所有基础数据值结合(全部基础数据，供管理员编辑)")
    @ApiImplicitParam(paramType="path", name = "type", value = "0：全部，其他：基础数据类型type值", required = true, dataType = "Integer")
    public ReturnVO<List<SetResponse>> mgList(@PathVariable("type") Integer type) {
        List<SetResponse> types = setService.allSetsByType(true, type);
        return ReturnVO.success(types);
    }

    @PostMapping("/mg/save")
    @AAuth(roles = {ERole.Manager})
    @ApiOperation(value = "保存基础数据", notes = "新增一条基础数据")
    public ReturnVO mgSave(@AHugh Employee e, @Validated(value = {Insert.class}) @RequestBody SetRequest request) {
        setService.save(e, request);
        return ReturnVO.success();
    }

    @PutMapping("/mg/update/{sId}")
    @AAuth(roles = {ERole.Manager})
    @ApiOperation(value = "更新基础数据", notes = "编辑基础数据")
    @ApiImplicitParam(paramType="path", name = "type", value = "当前SID值", required = true, dataType = "Integer")
    public ReturnVO mgUpdate(@AHugh Employee e, @PathVariable("sId") Integer sId,  @Validated(value = {Update.class}) @RequestBody SetRequest request) {
        setService.update(e, sId, request);
        return ReturnVO.success();
    }

    @PutMapping("/mg/enable/{sId}")
    @AAuth(roles = {ERole.Manager})
    @ApiOperation(value = "启用当前基础数据", notes = "启用当前基础数据")
    @ApiImplicitParam(paramType="path", name = "type", value = "当前SID值", required = true, dataType = "Integer")
    public ReturnVO mgEnable(@AHugh Employee e, @PathVariable("sId") Integer sId) {
        setService.switcher(e, sId, true);
        return ReturnVO.success();
    }


    @PutMapping("/mg/disable/{sId}")
    @AAuth(roles = {ERole.Manager})
    @ApiOperation(value = "禁用当前基础数据", notes = "禁用当前基础数据")
    @ApiImplicitParam(paramType="path", name = "type", value = "当前SID值", required = true, dataType = "Integer")
    public ReturnVO mgDisable(@AHugh Employee e, @PathVariable("sId") Integer sId) {
        setService.switcher(e, sId, false);
        return ReturnVO.success();
    }

}
