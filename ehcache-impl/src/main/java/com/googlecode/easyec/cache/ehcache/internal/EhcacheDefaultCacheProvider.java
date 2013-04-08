package com.googlecode.easyec.cache.ehcache.internal;

import com.googlecode.easyec.cache.*;
import com.googlecode.easyec.cache.util.CacheHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.ObjectExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * 默认的本地缓存配置提供者类。
 *
 * @author JunJie
 */
public class EhcacheDefaultCacheProvider implements CacheProvider {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected CacheManager cacheManager;

    public EhcacheDefaultCacheProvider(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public boolean put(String cacheName, Object cacheKey, Object value) throws CacheException {
        try {
            if (value != null) {
                if (value instanceof CacheElement) {
                    return put(cacheName, (CacheElement) value);
                }

                if (cacheKey == null) {
                    throw new CacheException("cache key is null.");
                }

                Cache cache = cacheManager.getCache(cacheName);
                if (value instanceof Element) {
                    cache.put((Element) value);
                } else {
                    cache.put(new Element(cacheKey, CacheHelper.writeObjectToBytes(value)));
                }

                return true;
            }

            logger.warn("Value of key: [" + cacheKey + "] is null when put into cache: [" + cacheName + "].");
            return false;
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    public boolean put(String cacheName, CacheElement element) throws CacheException {
        try {
            if (element != null) {
                if (element.getKey() == null) {
                    throw new CacheException("cache key is null.");
                }

                Element e = new Element(element.getKey(), CacheHelper.writeObjectToBytes(element));
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

    public Object get(String cacheName, Object cacheKey) throws CacheException {
        try {
            Element element = cacheManager.getCache(cacheName).get(cacheKey);
            if (element != null) {
                Object o = CacheHelper.readObjectFromBytes((byte[]) element.getValue());
                if (o instanceof CacheElement) {
                    CacheElement e = (CacheElement) o;
                    e.setHitCount(element.getHitCount());
                    e.setLastUpdateTime(element.getLastUpdateTime());
                }

                return o;
            }

            return null;
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
                return new EhcacheDefaultCacheStatistics(statistics);
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
