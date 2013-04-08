package com.googlecode.easyec.cache.ehcache.factory;

import com.googlecode.easyec.cache.CacheProvider;
import com.googlecode.easyec.cache.CacheService;
import com.googlecode.easyec.cache.ehcache.service.DefaultCacheService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

/**
 * EHCACHE的<code>CacheService</code>工厂Bean类。
 *
 * @author JunJie
 */
public class EhcacheCacheServiceFactoryBean implements FactoryBean<CacheService>, InitializingBean {

    private CacheService cacheService;
    private CacheProvider cacheProvider;
    private String globalCacheName;

    public CacheService getObject() throws Exception {
        return cacheService;
    }

    public Class<?> getObjectType() {
        return CacheService.class;
    }

    public boolean isSingleton() {
        return true;
    }

    /**
     * 设置一个全局的缓存区名字。
     * <p>
     * 该名字不为null且不为空字符串。指定的名字才会被作用到<code>CacheService</code>上。
     * </p>
     *
     * @param globalCacheName 全局缓存区的名字
     * @see DefaultCacheService#GLOBAL_CACHE_NAME
     */
    public void setGlobalCacheName(String globalCacheName) {
        this.globalCacheName = globalCacheName;
    }

    /**
     * 指定设置一个<code>CacheProvider</code>实现类。
     *
     * @param cacheProvider {@link CacheProvider}的实现类
     */
    public void setCacheProvider(CacheProvider cacheProvider) {
        this.cacheProvider = cacheProvider;
    }

    public void afterPropertiesSet() throws Exception {
        if (cacheProvider == null) {
            throw new IllegalArgumentException("CacheProvider cannot be null.");
        }

        cacheService = new DefaultCacheService(cacheProvider);

        if (StringUtils.hasText(globalCacheName)) {
            ((DefaultCacheService) cacheProvider).setGlobalCacheName(globalCacheName);
        }
    }
}
