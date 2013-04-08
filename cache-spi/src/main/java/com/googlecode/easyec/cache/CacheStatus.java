package com.googlecode.easyec.cache;

/**
 * 枚举缓存状态。
 *
 * @author JunJie
 */
public enum CacheStatus {

    /**
     * The cache has not yet been initialised
     */
    STATUS_UNINITIALISED,

    /**
     * The cache is alive
     */
    STATUS_ALIVE,

    /**
     * The cache has been shutdown
     */
    STATUS_SHUTDOWN,

    /**
     * The cache has been thrown an error
     */
    STATUS_ERROR,

    /**
     * The cache is unknown.
     */
    STATUS_UNKNOWN
}
