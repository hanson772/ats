package com.milu.ats.bean.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * @Author fitz.bai
 * @Date 2019-10-10 15:20
 */
@Data
@AllArgsConstructor
public class SearchBase<T> extends PageBase {
    private StringBuilder sql;
    private Map<String, Object> paras;
    private Class<T> clazz;
}
