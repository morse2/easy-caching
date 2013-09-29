package com.googlecode.easyec.cache;

/**
 * 缓存提供者接口类。
 *
 * @author JunJie
 */
public interface CacheProvider {

    /**
     * 向缓存中推入一个{@link CacheElement}对象。
     * <p>
     * 此对象可以指定缓存元素存在的时间。例如：活动失效时间和空闲失效时间
     * </p>
     *
     * @param cacheName 缓存区的名字
     * @param element   缓存元素对象
     * @return 推入成功，返回true；否则返回false
     * @throws CacheException 操作缓存时，发生错误，则抛出此异常
     */
    boolean put(String cacheName, CacheElement element) throws CacheException;

    /**
     * 从缓存中查找一个缓存对象。如果没找到缓存对象，应当返回null。
     *
     * @param cacheName 缓存区的名字
     * @param cacheKey  缓存区中的缓存名
     * @return 缓存元素对象
     * @throws CacheException 操作缓存时，发生错误，则抛出此异常
     */
    CacheElement get(String cacheName, Object cacheKey) throws CacheException;

    /**
     * 从缓存中删除一个缓存对象。
     *
     * @param cacheName 缓存区的名字
     * @param cacheKey  缓存区中的缓存名
     * @return 删除成功，返回true；否则返回false
     * @throws CacheException 操作缓存时，发生错误，则抛出此异常
     */
    boolean remove(String cacheName, Object cacheKey) throws CacheException;

    /**
     * 删除一个缓存区中的所有缓存对象。
     *
     * @param cacheName 缓存区的名字
     * @return 删除成功，返回true；否则返回false
     * @throws CacheException 操作缓存时，发生错误，则抛出此异常
     */
    boolean removeAll(String cacheName) throws CacheException;

    /**
     * 得到缓存区的当前状态。
     *
     * @param cacheName 缓存区的名字
     * @return {@link CacheStatus}
     * @throws CacheException 操作缓存时，发生错误，则抛出此异常
     */
    CacheStatus getStatus(String cacheName) throws CacheException;

    /**
     * 获取指定缓存区的统计信息。
     * <p>
     * 如果缓存区不支持缓存统计，则抛出异常
     * </p>
     *
     * @param cacheName 缓存区名
     * @return 缓存区的统计信息
     * @throws CacheException 操作缓存时，发生错误，则抛出此异常
     */
    CacheStatistics getStatistics(String cacheName) throws CacheException;

    /**
     * 添加一个新的缓存区的方法。
     * <p>
     * 如果缓存区不存在，则新增；否则忽略新增操作
     * </p>
     *
     * @param cacheName 缓存区名
     * @return 新增成功，返回真；否则返回假
     * @throws CacheException 操作缓存时，发生错误，则抛出此异常
     */
    boolean addCacheIfAbsent(String cacheName) throws CacheException;
}
