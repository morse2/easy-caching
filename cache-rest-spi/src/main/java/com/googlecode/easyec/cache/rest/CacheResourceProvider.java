package com.googlecode.easyec.cache.rest;

import com.googlecode.easyec.cache.*;
import com.googlecode.easyec.cache.rest.jaxb.CacheObject;
import com.googlecode.easyec.cache.serializer.SerializerFactory;
import com.googlecode.easyec.cache.serializer.impl.DefaultSerializerFactory;
import org.jboss.resteasy.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RESTful缓存资源提供者类
 *
 * @author JunJie
 */
public class CacheResourceProvider implements CacheProvider {

    private static final Logger logger = LoggerFactory.getLogger(CacheResourceProvider.class);
    private SerializerFactory serializerFactory = new DefaultSerializerFactory();
    private CacheResource cacheResource;

    public CacheResourceProvider(CacheResource cacheResource) {
        this(cacheResource, null);
    }

    public CacheResourceProvider(CacheResource cacheResource, SerializerFactory serializerFactory) {
        this.cacheResource = cacheResource;

        if (null != serializerFactory) {
            this.serializerFactory = serializerFactory;
        }
    }

    public boolean put(String cacheName, Object cacheKey, Object value) throws CacheException {
        try {
            if (cacheKey == null) {
                logger.warn("cache key is null, so ignore put operation.");

                return false;
            }

            cacheResource.put(new CacheObject(cacheName, String.valueOf(cacheKey), serializerFactory.writeObject(value)));

            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new CacheException(e);
        }
    }

    public boolean put(String cacheName, CacheElement element) throws CacheException {
        try {
            if (element == null) {
                logger.warn("Cache element is null, so ignore put operation.");

                return false;
            }

            Object key = element.getKey();
            if (key == null) {
                logger.warn("Cache key in CacheElement object is null, so ignore put operation.");
            }

            cacheResource.put(new CacheObject(cacheName, String.valueOf(key), serializerFactory.writeObject(element)));

            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new CacheException(e);
        }
    }

    public Object get(String cacheName, Object cacheKey) throws CacheException {
        if (cacheKey == null) return null;

        ClientResponse res = (ClientResponse) cacheResource.get(cacheName, String.valueOf(cacheKey));

        try {
            Object entity = res.getEntity(CacheObject.class);

            return entity != null ? serializerFactory.readObject(((CacheObject) entity).getSer()) : null;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new CacheException(e);
        }
    }

    public boolean remove(String cacheName, Object cacheKey) throws CacheException {
        try {
            if (cacheKey == null) {
                logger.warn("Cache key is null, so ignore remove operation.");

                return false;
            }

            cacheResource.remove(new CacheObject(cacheName, String.valueOf(cacheKey), null));

            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new CacheException(e);
        }
    }

    public boolean removeAll(String cacheName) throws CacheException {
        throw new UnsupportedOperationException("cache name: [" + cacheName + "].");
    }

    public CacheStatus getStatus(String cacheName) throws CacheException {
        return CacheStatus.valueOf((String) cacheResource.status(cacheName).getEntity());
    }

    public CacheStatistics getStatistics(String cacheName) throws CacheException {
        throw new UnsupportedOperationException("cache name: [" + cacheName + "].");
    }

    public boolean addCacheIfAbsent(String cacheName) throws CacheException {
        return (Boolean) cacheResource.addCacheIfAbsent(cacheName).getEntity();
    }
}
