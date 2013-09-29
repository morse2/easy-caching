package com.googlecode.easyec.cache.taglibs.tag;

import com.googlecode.easyec.cache.taglibs.support.PageFragmentBodyTagSupport;

/**
 * JSP页面片段缓存标签类
 *
 * @author JunJie
 */
public class PageFragmentBodyTag extends PageFragmentBodyTagSupport {

    private static final long serialVersionUID = -8814341365430053404L;

    /**
     * 设置缓存区名
     *
     * @param cacheName 缓存区名
     */
    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    /**
     * 设置缓存键
     *
     * @param cacheKey 缓存键
     */
    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    /**
     * 设置活动失效时间，单位为秒
     *
     * @param timeToLive 活动失效时间
     */
    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    /**
     * 设置闲置失效时间，单位为秒
     *
     * @param timeToIdle 闲置失效时间
     */
    public void setTimeToIdle(int timeToIdle) {
        this.timeToIdle = timeToIdle;
    }
}
