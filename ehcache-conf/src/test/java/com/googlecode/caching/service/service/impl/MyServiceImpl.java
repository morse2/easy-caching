package com.googlecode.caching.service.service.impl;

import com.googlecode.caching.service.service.MyService;
import com.googlecode.easyec.cache.annotation.CachePut;
import com.googlecode.easyec.cache.annotation.Cacheable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-20
 * Time: 上午10:20
 * To change this template use File | Settings | File Templates.
 */
public class MyServiceImpl implements MyService {

    @Cacheable(
            value = { "globalCache" },
            key = "T(com.googlecode.easyec.cache.ehcache.test.CacheKey).MY_KEY + #name"
    )
    public String print0(String name) {
        return "Hello print0. " + name;
    }

    @CachePut(
            value = { "globalCache" },
            key = "T(com.googlecode.easyec.cache.ehcache.test.CacheKey).MY_KEY + #name"
    )
    public String print1(String name) {
        return "Hello print1. " + name;
    }
}
