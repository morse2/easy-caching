package com.googlecode.easyec.cache;

/**
 * 缓存服务定义接口。
 *
 * @author JunJie
 */
public interface CacheService {

    /**
     * 通过给定的key，从全局的cache中获取缓存的内容。
     *
     * @param key 缓存的key
     * @return key对应的值内容
     */
    Object get(Object key);

    /**
     * 通过给定的缓存名和key，获取缓存的内容。
     *
     * @param cacheName 缓存名
     * @param key       缓存的key
     * @return key对应的值内容
     */
    Object get(String cacheName, Object key);

    /**
     * 向全局的缓存中存放一个缓存对象。
     *
     * @param key    缓存的key名字
     * @param object key对应的值内容
     * @return 存放成功，返回true；否则返回false
     */
    boolean put(Object key, Object object);

    /**
     * 向给定的缓存名中存放一个缓存对象。
     *
     * @param cacheName 缓存名
     * @param key       缓存的key名字
     * @param object    key对应的值内容
     * @return 存放成功，返回true；否则返回false
     */
    boolean put(String cacheName, Object key, Object object);

    /**
     * 通过给定的缓存名，清空此缓存中缓存的信息。
     *
     * @param cacheName 缓存名
     * @return 清空指定缓存名中的所有缓存信息
     */
    boolean removeAll(String cacheName);

    /**
     * 查看全局缓存的状态。
     *
     * @return 缓存的状态信息
     */
    CacheStatus getCacheStatus();

    /**
     * 查看指定的缓存状态。
     *
     * @param cacheName 缓存名
     * @return 缓存的状态信息
     */
    CacheStatus getCacheStatus(String cacheName);

    /**
     * 通过给定的key，清除全局的缓存信息。
     *
     * @param cacheKey 缓存的key
     * @return 移除缓存成功，返回true；否则返回false
     */
    boolean removeCache(Object cacheKey);

    /**
     * 通过给定的缓存名和key，清除其缓存信息。
     *
     * @param cacheName 缓存名
     * @param cacheKey  缓存的key
     * @return 移除缓存成功，返回true；否则返回false
     */
    boolean removeCache(String cacheName, Object cacheKey);

    /**
     * 获取默认缓存区的统计信息。
     * <p>
     * 如果缓存区不支持缓存统计，则返回null
     * </p>
     *
     * @return 缓存区的统计信息
     */
    CacheStatistics getStatistics();

    /**
     * 获取指定缓存区的统计信息。
     * <p>
     * 如果缓存区不支持缓存统计，则返回null
     * </p>
     *
     * @param cacheName 缓存区名
     * @return 缓存区的统计信息
     */
    CacheStatistics getStatistics(String cacheName);
}
