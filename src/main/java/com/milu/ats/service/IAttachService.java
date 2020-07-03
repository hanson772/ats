package com.milu.ats.service;

import com.milu.ats.dal.entity.AttachDO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author max.chen
 * @class
 */
public interface IAttachService {
    AttachDO upload(MultipartFile file);

    void preview(int aId, HttpServletResponse response);

    AttachDO findById(int aid);

    AttachDO findByCode(String code);
}
