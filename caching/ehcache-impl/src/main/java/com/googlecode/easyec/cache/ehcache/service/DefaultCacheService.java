package com.googlecode.easyec.cache.ehcache.service;

import com.googlecode.easyec.cache.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认的缓存服务实现类。
 * <p>
 * 通过构造传递{@link CacheProvider}的实例，实现通用的缓存的方法调用。
 * </p>
 *
 * @author JunJie
 */
public class DefaultCacheService implements CacheService {

    /**
     * 全局默认的缓存名
     */
    public static final String GLOBAL_CACHE_NAME = "globalCache";

    private static final Logger logger = LoggerFactory.getLogger(DefaultCacheService.class);
    private String globalCacheName = GLOBAL_CACHE_NAME;
    private CacheProvider cacheProvider;

    public DefaultCacheService(CacheProvider cacheProvider) {
        this.cacheProvider = cacheProvider;
    }

    /**
     * 设置全局缓存区的名字
     *
     * @param globalCacheName 全局缓存区的名字
     */
    public void setGlobalCacheName(String globalCacheName) {
        this.globalCacheName = globalCacheName;
    }

    public Object get(Object key) {
        return get(globalCacheName, key);
    }

    public Object get(String cacheName, Object key) {
        try {
            return cacheProvider.get(cacheName, key);
        } catch (CacheException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public boolean put(Object key, Object object) {
        return put(globalCacheName, key, object);
    }

    public boolean put(String cacheName, Object key, Object object) {
        try {
            return cacheProvider.put(cacheName, key, object);
        } catch (CacheException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public boolean removeAll(String cacheName) {
        try {
            return cacheProvider.removeAll(cacheName);
        } catch (CacheException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public CacheStatus getCacheStatus() {
        return getCacheStatus(globalCacheName);
    }

    public CacheStatus getCacheStatus(String cacheName) {
        try {
            return cacheProvider.getStatus(cacheName);
        } catch (CacheException e) {
            logger.error(e.getMessage(), e);
            return CacheStatus.STATUS_ERROR;
        }
    }

    public boolean removeCache(Object cacheKey) {
        return removeCache(globalCacheName, cacheKey);
    }

    public boolean removeCache(String cacheName, Object cacheKey) {
        try {
            return cacheProvider.remove(cacheName, cacheKey);
        } catch (CacheException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public CacheStatistics getStatistics() {
        return getStatistics(globalCacheName);
    }

    public CacheStatistics getStatistics(String cacheName) {
        try {
            return cacheProvider.getStatistics(cacheName);
        } catch (CacheException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
