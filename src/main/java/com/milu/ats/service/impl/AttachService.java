package com.milu.ats.service.impl;

import com.milu.ats.bean.pojo.EMessage;
import com.milu.ats.dal.entity.AttachDO;
import com.milu.ats.dal.repository.AttachRepository;
import com.milu.ats.service.IAttachService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author max.chen
 * @class
 */
@Service
@Slf4j
public class AttachService implements IAttachService {
    @Autowired
    AttachRepository attachRepository;

    /**
     * 上传附件并保存
     * @param file
     * @return
     */
    @Override
    public AttachDO upload(MultipartFile file){

        //TODO 上传附件
        String dispay = file.getOriginalFilename();
        String mime = file.getContentType();
        long size = file.getSize();
        String code= uploadToDisk(file);
        AttachDO entity = AttachDO.builder()
                .code(code)
                .display(dispay)
                .mime(mime)
                .size(size)
                .build();

        attachRepository.saveAndFlush(entity);
        return entity;
    }

    /**
     * 附件预览
     * @param aId
     * @param response
     */
    @Override
    public void preview(int aId, HttpServletResponse response){
        AttachDO entity = findById(aId);
        Assert.notNull(entity, EMessage.ATTACH_NOT_EXIST.show());
        // TODO 文件预览
    }

    /**
     * 根据id查询附件
     * @param aid
     * @return
     */
    @Override
    public AttachDO findById(int aid){
        return aid > 0 ? attachRepository.findById(aid).orElse(null) : null;
    }

    /**
     * 根据code 查询附件
     * @param code
     * @return
     */
    @Override
    public AttachDO findByCode(String code){
        return StringUtils.hasText(code) ? attachRepository.findByCode(code).orElse(null) : null;
    }


    /**
     * 上传文件
     * @param file
     * @return
     */
    private String uploadToDisk(MultipartFile file){
        //TODO
        return UUID.randomUUID().toString().replace("-", "");
    }
}
