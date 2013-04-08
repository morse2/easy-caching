package com.googlecode.easyec.cache.rest.impl;

import com.googlecode.easyec.cache.CacheService;
import com.googlecode.easyec.cache.CacheStatus;
import com.googlecode.easyec.cache.rest.CacheResource;
import com.googlecode.easyec.cache.rest.jaxb.CacheObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.googlecode.easyec.cache.CacheStatus.STATUS_UNKNOWN;
import static com.googlecode.easyec.cache.ehcache.service.DefaultCacheService.GLOBAL_CACHE_NAME;
import static org.springframework.util.StringUtils.hasText;

/**
 * Created with IntelliJ IDEA.
 * User: JunJie
 * Date: 12-8-6
 * Time: 下午11:11
 * To change this template use File | Settings | File Templates.
 */
@Path("/")
public class CacheResourceImpl implements CacheResource, InitializingBean {

    /**
     * log object
     */
    private static final Logger logger = LoggerFactory.getLogger(CacheResourceImpl.class);

    private String globalCacheName;

    public void setGlobalCacheName(String globalCacheName) {
        this.globalCacheName = globalCacheName;
    }

    @Autowired
    private CacheService cacheService;

    @PUT
    @Path("put")
    public void put(CacheObject object) {
        if (object == null) {
            logger.error("CacheObject is null.");
            return;
        }

        String cacheName = hasText(object.getCacheName()) ? object.getCacheName() : globalCacheName;

        logger.debug("Cache name: [" + cacheName + "], cache key: [" + object.getKey() + "]");

        CacheStatus status = cacheService.getCacheStatus(cacheName);
        switch (status) {
            case STATUS_ERROR:
                logger.error("Cache's status is error. cache name: [" + cacheName + "].");
                break;
            case STATUS_SHUTDOWN:
                logger.error("Cache's status is shutdown. cache name: [" + cacheName + "].");
                break;
            case STATUS_UNKNOWN:
                logger.error("Unknown status of cache. cache name: [" + cacheName + "].");
                break;
            case STATUS_ALIVE:
                logger.info("Put into cache. cache name: [" + cacheName + "].");
                boolean b = cacheService.put(
                        cacheName,
                        object.getKey(),
                        object.getSer()
                );

                logger.info("Result of putting operation. [" + b + "].");
        }
    }

    @GET
    @Path("get/{key}/{cacheName}")
    @Produces({ MediaType.APPLICATION_XML })
    public Response get(@PathParam("cacheName") String cacheName, @PathParam("key") String key) {
        if (!hasText(key)) {
            logger.error("cache key is null.");

            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (!hasText(cacheName)) {
            cacheName = globalCacheName;
        }

        logger.debug("Cache name: [" + cacheName + "], cache key: [" + key + "]");

        CacheStatus status = cacheService.getCacheStatus(cacheName);
        switch (status) {
            case STATUS_ERROR:
                logger.error("Cache's status is error. cache name: [" + cacheName + "].");
                break;
            case STATUS_SHUTDOWN:
                logger.error("Cache's status is shutdown. cache name: [" + cacheName + "].");
                break;
            case STATUS_UNKNOWN:
                logger.error("Unknown status of cache. cache name: [" + cacheName + "].");
                break;
            case STATUS_ALIVE:
                logger.info("Get into cache. cache name: [" + cacheName + "].");
                Object v = cacheService.get(cacheName, key);

                try {
                    return Response.ok(new CacheObject(cacheName, key, v)).build();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);

                    return Response.serverError().build();
                }
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("remove")
    public void remove(CacheObject object) {
        if (object == null) {
            logger.error("CacheObject is null.");
            return;
        }

        String cacheName = hasText(object.getCacheName()) ? object.getCacheName() : globalCacheName;

        logger.debug("Cache name: [" + cacheName + "], cache key: [" + object.getKey() + "]");

        CacheStatus status = cacheService.getCacheStatus(cacheName);
        switch (status) {
            case STATUS_ERROR:
                logger.error("Cache's status is error. cache name: [" + cacheName + "].");
                break;
            case STATUS_SHUTDOWN:
                logger.error("Cache's status is shutdown. cache name: [" + cacheName + "].");
                break;
            case STATUS_UNKNOWN:
                logger.error("Unknown status of cache. cache name: [" + cacheName + "].");
                break;
            case STATUS_ALIVE:
                logger.info("Remove into cache. cache name: [" + cacheName + "].");

                boolean b = false;
                if (object.getKey() != null) {
                    b = cacheService.removeCache(cacheName, object.getKey());
                }

                logger.info("Result of removing operation. [" + b + "].");
        }
    }

    @GET
    @Path("status/{cacheName}")
    @Produces({ MediaType.TEXT_PLAIN })
    public Response status(@PathParam("cacheName") String cacheName) {
        if (!hasText(cacheName)) {
            logger.error("Cache name is null.");

            return Response.ok(STATUS_UNKNOWN).build();
        }

        CacheStatus status = cacheService.getCacheStatus(cacheName);

        logger.info("Current status is: [" + status + "].");

        return Response.ok(status).build();
    }

    public void afterPropertiesSet() throws Exception {
        if (!hasText(globalCacheName)) {
            globalCacheName = GLOBAL_CACHE_NAME;
        }
    }
}
