package com.milu.ats.service;

import com.milu.ats.bean.pojo.SearchBase;
import com.milu.ats.bean.response.PageResponse;

/**
 * @author max.chen
 * @class
 */
public interface ISearchService {
    <T> PageResponse<T> searchByPage(SearchBase<T> searchBase);
}
