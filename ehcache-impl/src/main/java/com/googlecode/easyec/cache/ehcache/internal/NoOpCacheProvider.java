package com.googlecode.easyec.cache.ehcache.internal;

import com.googlecode.easyec.cache.*;

/**
 * 默认不做缓存的服务提供者实现类。
 *
 * @author JunJie
 */
public class NoOpCacheProvider implements CacheProvider {

    public boolean put(String cacheName, CacheElement element) throws CacheException {
        return true;
    }

    public CacheElement get(String cacheName, Object cacheKey) throws CacheException {
        return null;
    }

    public boolean remove(String cacheName, Object cacheKey) throws CacheException {
        return true;
    }

    public boolean removeAll(String cacheName) throws CacheException {
        return true;
    }

    public CacheStatus getStatus(String cacheName) throws CacheException {
        return CacheStatus.STATUS_UNKNOWN;
    }

    public CacheStatistics getStatistics(String cacheName) throws CacheException {
        return null;
    }

    public boolean addCacheIfAbsent(String cacheName) throws CacheException {
        return true;
    }
}
