package com.milu.ats.bean.request;

import lombok.Data;

/**
 * @Author fitz.bai
 * @Date 2019-10-10 15:20
 */
@Data
public class PageBase {
    private Integer pageNo = 1;
    private Integer pageSize = 10;
}
