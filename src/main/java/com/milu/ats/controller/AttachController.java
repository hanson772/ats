package com.milu.ats.controller;

import com.milu.ats.bean.ReturnVO;
import com.milu.ats.bean.pojo.Easy;
import com.milu.ats.dal.entity.AttachDO;
import com.milu.ats.service.IAttachService;
import com.milu.ats.util.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author max.chen
 * @class
 */
@Slf4j
@RestController
@RequestMapping("attach")
@Api(tags = "附件", description = "附件上传、下载、预览")
public class AttachController {
    @Autowired
    IAttachService attachService;
    /********************投递阶段**********************/

    @PostMapping("/upload")
    @ApiOperation(value = "上传附件", notes = "上传附件")
    public ReturnVO<Easy> upload(@RequestParam("file") MultipartFile file) {
        AttachDO entity = attachService.upload(file);
        Easy easy = Easy.builder().key(Tools.idEncode(entity.getId())).value(entity.getDisplay()).build();
        return ReturnVO.success(easy);
    }
}
