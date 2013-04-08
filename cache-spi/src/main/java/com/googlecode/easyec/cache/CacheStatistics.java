package com.googlecode.easyec.cache;

import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * 展示缓存统计信息的接口类。
 *
 * @author JunJie
 */
@XmlType
public abstract class CacheStatistics implements Serializable {

    private static final long serialVersionUID = 2727146290680997764L;

    /**
     * 平均读取缓存对象的时间。
     *
     * @return <code>float</code>
     */
    public abstract float getAverageGetTime();

    /**
     * 平均搜索次数。
     *
     * @return <code>long</code>
     */
    public abstract long getAverageSearchTime();

    /**
     * 缓存击中数。
     *
     * @return <code>long</code>
     */
    public abstract long getCacheHits();

    /**
     * 缓存未击中数。
     *
     * @return <code>long</code>
     */
    public abstract long getCacheMisses();

    /**
     * 存于磁盘上的元素数量。
     *
     * @return <code>long</code>
     */
    public abstract long getDiskStoreObjectCount();

    /**
     * 缓存失效数量。
     *
     * @return <code>long</code>
     */
    public abstract long getEvictionCount();

    /**
     * 缓存元素在内存中的击中数。
     *
     * @return <code>long</code>
     */
    public abstract long getInMemoryHits();

    /**
     * 缓存元素在内存中未击中数。
     *
     * @return <code>long</code>
     */
    public abstract long getInMemoryMisses();

    /**
     * 存于内存中的缓存元素数量。
     *
     * @return <code>long</code>
     */
    public abstract long getMemoryStoreObjectCount();

    /**
     * 缓存元素数量。
     *
     * @return <code>long</code>
     */
    public abstract long getObjectCount();

    /**
     * 缓存元素在关闭堆栈中的击中数。
     *
     * @return <code>long</code>
     */
    public abstract long getOffHeapHits();

    /**
     * 缓存元素在关闭堆栈中的未击中数。
     *
     * @return <code>long</code>
     */
    public abstract long getOffHeapMisses();

    /**
     * 存于关闭堆栈中的缓存元素数量。
     *
     * @return <code>long</code>
     */
    public abstract long getOffHeapStoreObjectCount();

    /**
     * 缓存元素在磁盘中的击中数。
     *
     * @return <code>long</code>
     */
    public abstract long getOnDiskHits();

    /**
     * 缓存元素在磁盘中的未击中数。
     *
     * @return <code>long</code>
     */
    public abstract long getOnDiskMisses();

    /**
     * 每秒搜索缓存元素的数量。
     *
     * @return <code>long</code>
     */
    public abstract long getSearchesPerSecond();
}
