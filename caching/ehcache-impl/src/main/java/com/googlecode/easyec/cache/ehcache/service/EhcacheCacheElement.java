package com.googlecode.easyec.cache.ehcache.service;

import com.googlecode.easyec.cache.CacheElement;

/**
 * Ehcache的缓存元素对象。
 *
 * @author JunJie
 */
public class EhcacheCacheElement extends CacheElement {

    private static final long serialVersionUID = 8984378953563260176L;

    public EhcacheCacheElement() {
        // no op
    }

    public EhcacheCacheElement(Object key, Object value) {
        super(key, value);
    }

    public EhcacheCacheElement(Object key, Object value, int timeToLive, int timeToIdle) {
        super(key, value, timeToLive, timeToIdle);
    }
}
