package com.milu.ats.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author max.chen
 * @class
 */
@Component
public class CacheConfig {
    public static final String COOKIE_ALIAS = "user-cookie";

    static Cache<String, Object> dayCache = CacheBuilder.newBuilder()
            .maximumSize(1000) // 设置缓存的最大容量
            .expireAfterWrite(1,TimeUnit.DAYS) // 设置缓存在写入一分钟后失效
            .concurrencyLevel(10) // 设置并发级别为10
            .recordStats() // 开启缓存统计
            .build();

    public synchronized void put(String key, Object value) {
        dayCache.put(key, value);
    }

    public synchronized Object get(String key) {
        return dayCache.getIfPresent(key);
    }

    public synchronized void remove(String key){
        dayCache.invalidate(key);
    }
}
