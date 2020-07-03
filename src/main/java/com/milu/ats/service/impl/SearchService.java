package com.milu.ats.service.impl;

import com.milu.ats.bean.pojo.SearchBase;
import com.milu.ats.bean.response.PageResponse;
import com.milu.ats.service.ISearchService;
import com.milu.ats.util.Pooler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author max.chen
 * @class
 */
@Service
@Slf4j
public class SearchService implements ISearchService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public  <T> PageResponse<T> searchByPage(SearchBase<T> searchBase){
        Integer pageNo = 1;
        Integer pageSize = 10;
        Integer count = 0;
        List result = new ArrayList();

        pageNo = searchBase.getPageNo() == null ? pageNo : searchBase.getPageNo();
        pageSize = searchBase.getPageSize() == null ? pageSize : searchBase.getPageSize();
        try {
            TypedQuery countQuery = buildQuery(searchBase);
            CompletableFuture<Integer> countTask = Pooler.async(() -> {
                return countQuery.getResultList().size();
            });

            TypedQuery query = buildQuery(searchBase);
            query.setFirstResult(pageSize * (pageNo - 1));
            query.setMaxResults(pageSize);
            result = query.getResultList();
            count = countTask.get();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("搜索出错：{}", ex.getMessage());
            Assert.isTrue(false, ex.getMessage());
        } finally {
            entityManager.close();
        }

        PageResponse csr = new PageResponse();
        csr.setCount(count);
        csr.setList(result);
        csr.setPageNo(pageNo);
        csr.setPageSize(pageSize);
        return csr;
    }

    private <T>  TypedQuery<T> buildQuery(SearchBase<T> searchBase){
        TypedQuery<T> query = entityManager.createQuery(searchBase.getSql().toString(), searchBase.getClazz());
        Map<String, Object> paras = searchBase.getParas();
        for (String param : paras.keySet()) {
            query.setParameter(param, paras.get(param));
        }
        return query;
    }
}
