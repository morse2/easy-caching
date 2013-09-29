package com.googlecode.easyec.cache.ehcache.distribution;

import com.googlecode.easyec.cache.*;

/**
 * 分布式的Ehcache缓存提供者实现类。
 * <p>
 * 此分布式方式为嵌入式的缓存集群。集群方式有两种：
 * <ol>
 * <li>使用默认的RMI方式同步缓存</li>
 * <li>使用JMS消息方式同步应用中的缓存</li>
 * </ol>
 * </p>
 *
 * @author JunJie
 */
public class EhcacheCacheProviderDecorator implements CacheProvider {

    private CacheProvider cacheProvider;

    public EhcacheCacheProviderDecorator(CacheProvider cacheProvider) {
        this.cacheProvider = cacheProvider;
    }

    public boolean put(String cacheName, CacheElement element) throws CacheException {
        return cacheProvider.put(cacheName, element);
    }

    public CacheElement get(String cacheName, Object cacheKey) throws CacheException {
        return cacheProvider.get(cacheName, cacheKey);
    }

    public boolean remove(String cacheName, Object cacheKey) throws CacheException {
        return cacheProvider.remove(cacheName, cacheKey);
    }

    public boolean removeAll(String cacheName) throws CacheException {
        return cacheProvider.removeAll(cacheName);
    }

    public CacheStatus getStatus(String cacheName) throws CacheException {
        return cacheProvider.getStatus(cacheName);
    }

    public CacheStatistics getStatistics(String cacheName) throws CacheException {
        return cacheProvider.getStatistics(cacheName);
    }

    public boolean addCacheIfAbsent(String cacheName) throws CacheException {
        return cacheProvider.addCacheIfAbsent(cacheName);
    }
}
