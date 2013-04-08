package com.googlecode.easyec.cache.ehcache.interceptor;

import com.googlecode.easyec.cache.interceptor.CacheCallback;

import static com.googlecode.easyec.cache.ehcache.service.DefaultCacheService.GLOBAL_CACHE_NAME;

/**
 * EHCACHE抽象的缓存回调实现类。
 * 此类默认实现了{@link CacheCallback#getCaches()}，
 * 返回全局的缓存区名。
 *
 * @author JunJie
 */
public abstract class EhcacheCacheCallback<T> implements CacheCallback<T> {

    public String[] getCaches() {
        return new String[] { GLOBAL_CACHE_NAME };
    }
}
