package com.googlecode.easyec.cache.interceptor;

import com.googlecode.easyec.cache.CacheElement;

/**
 * 注解形式缓存元素类。
 *
 * @author JunJie
 */
class AnnotatedCacheElement extends CacheElement {

    private static final long serialVersionUID = 6869917290210954015L;

    public AnnotatedCacheElement(Object key, Object value) {
        super(key, value);
    }
}
