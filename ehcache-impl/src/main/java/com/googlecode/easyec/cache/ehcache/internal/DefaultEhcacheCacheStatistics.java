package com.googlecode.easyec.cache.ehcache.internal;

import com.googlecode.easyec.cache.CacheStatistics;

/**
 * 默认缓存区统计信息实现类。
 */
public class DefaultEhcacheCacheStatistics extends CacheStatistics {

    private static final long serialVersionUID = 5304085968934696681L;
    private net.sf.ehcache.Statistics statistics;

    public DefaultEhcacheCacheStatistics(net.sf.ehcache.Statistics statistics) {
        this.statistics = statistics;
    }

    public float getAverageGetTime() {
        return statistics.getAverageGetTime();
    }

    public long getAverageSearchTime() {
        return statistics.getAverageSearchTime();
    }

    public long getCacheHits() {
        return statistics.getCacheHits();
    }

    public long getCacheMisses() {
        return statistics.getCacheMisses();
    }

    public long getDiskStoreObjectCount() {
        return statistics.getDiskStoreObjectCount();
    }

    public long getEvictionCount() {
        return statistics.getEvictionCount();
    }

    public long getInMemoryHits() {
        return statistics.getInMemoryHits();
    }

    public long getInMemoryMisses() {
        return statistics.getInMemoryMisses();
    }

    public long getMemoryStoreObjectCount() {
        return statistics.getMemoryStoreObjectCount();
    }

    public long getObjectCount() {
        return statistics.getObjectCount();
    }

    public long getOffHeapHits() {
        return statistics.getOffHeapHits();
    }

    public long getOffHeapMisses() {
        return statistics.getOffHeapMisses();
    }

    public long getOffHeapStoreObjectCount() {
        return statistics.getOffHeapStoreObjectCount();
    }

    public long getOnDiskHits() {
        return statistics.getOnDiskHits();
    }

    public long getOnDiskMisses() {
        return statistics.getOnDiskMisses();
    }

    public long getSearchesPerSecond() {
        return statistics.getSearchesPerSecond();
    }
}
