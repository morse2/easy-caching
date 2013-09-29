package com.googlecode.easyec.cache.ehcache.internal;

import com.googlecode.easyec.cache.*;
import com.googlecode.easyec.cache.serializer.SerializerFactory;
import com.googlecode.easyec.cache.serializer.impl.NoOpSerializerFactory;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.ObjectExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * 默认的本地缓存配置提供者类。
 *
 * @author JunJie
 */
public class DefaultEhcacheCacheProvider implements CacheProvider {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected SerializerFactory serializerFactory = new NoOpSerializerFactory();
    protected CacheManager cacheManager;

    public DefaultEhcacheCacheProvider(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public DefaultEhcacheCacheProvider(CacheManager cacheManager, SerializerFactory serializerFactory) {
        Assert.notNull(serializerFactory, "SerializerFactory object cannot be null.");
        this.cacheManager = cacheManager;
        this.serializerFactory = serializerFactory;
    }

    public boolean put(String cacheName, CacheElement element) throws CacheException {
        try {
            if (null != element) {
                Element e = new Element(element.getKey(), serializerFactory.writeObject(element));
                if (element.getTimeToIdle() > 0) {
                    e.setTimeToIdle(element.getTimeToIdle());
                }

                if (element.getTimeToLive() > 0) {
                    e.setTimeToLive(element.getTimeToLive());
                }

                cacheManager.getCache(cacheName).put(e);
                return true;
            }

            logger.warn("Cache element is null when put into cache: [" + cacheName + "].");
            return false;
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public CacheElement get(String cacheName, Object cacheKey) throws CacheException {
        try {
            Element element = cacheManager.getCache(cacheName).get(cacheKey);
            if (null == element) return null;

            CacheElement e = (CacheElement) serializerFactory.readObject(element.getObjectValue());
            return new CacheElement.Builder(e.getKey(), e.getValue())
                .hitCount(element.getHitCount())
                .lastUpdateTime(element.getLastUpdateTime())
                .build();
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    public boolean remove(String cacheName, Object cacheKey) throws CacheException {
        try {
            return cacheManager.getCache(cacheName).remove(cacheKey);
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    public boolean removeAll(String cacheName) throws CacheException {
        try {
            cacheManager.getCache(cacheName).removeAll();
            return true;
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    public CacheStatus getStatus(String cacheName) throws CacheException {
        try {
            return CacheStatus.valueOf(cacheManager.getCache(cacheName).getStatus().toString());
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    public CacheStatistics getStatistics(String cacheName) throws CacheException {
        try {
            net.sf.ehcache.Statistics statistics = cacheManager.getCache(cacheName).getStatistics();
            if (statistics != null) {
                return new DefaultEhcacheCacheStatistics(statistics);
            }

            return null;
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    public boolean addCacheIfAbsent(String cacheName) throws CacheException {
        try {
            if (isBlank(cacheName)) return false;
            cacheManager.addCache(cacheName);

            return true;
        } catch (IllegalStateException e) {
            throw new CacheException(e);
        } catch (ObjectExistsException e) {
            logger.warn(e.getMessage());

            return false;
        } catch (net.sf.ehcache.CacheException e) {
            throw new CacheException(e);
        }
    }
}
