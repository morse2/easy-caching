package com.googlecode.easyec.cache;

import java.io.Serializable;

/**
 * 缓存元素类。
 *
 * @author JunJie
 * @since 0.2.2
 */
public abstract class CacheElement implements Serializable {

    private static final long serialVersionUID = 1242848471305382625L;
    protected Object key;
    protected Object value;
    private int timeToLive;
    private int timeToIdle;
    private transient long hitCount;
    private transient long lastUpdateTime;

    protected CacheElement() {
        // no op
    }

    /**
     * 构造方法。
     *
     * @param key   缓存元素的键
     * @param value 缓存元素的值
     */
    protected CacheElement(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 构造方法。
     *
     * @param key        缓存元素的键
     * @param value      缓存元素的值
     * @param timeToLive 活动失效时间
     * @param timeToIdle 闲置失效时间
     */
    protected CacheElement(Object key, Object value, int timeToLive, int timeToIdle) {
        this.key = key;
        this.value = value;
        this.timeToLive = timeToLive;
        this.timeToIdle = timeToIdle;
    }

    /**
     * 构造方法。
     *
     * @param key            缓存元素的键
     * @param value          缓存元素的值
     * @param timeToLive     活动失效时间
     * @param timeToIdle     闲置失效时间
     * @param lastUpdateTime 最后缓存的更新时间
     */
    protected CacheElement(Object key, Object value, int timeToLive, int timeToIdle, long lastUpdateTime) {
        this.key = key;
        this.value = value;
        this.timeToLive = timeToLive;
        this.timeToIdle = timeToIdle;
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * 得到缓存元素的键。
     *
     * @return 对象<code>Object</code>形式的键
     */
    public Object getKey() {
        return key;
    }

    /**
     * 得到缓存元素的值。
     *
     * @return 对象<code>Object</code>形式的值
     */
    public Object getValue() {
        return value;
    }

    /**
     * 得到活动失效时间。
     *
     * @return 精确到毫秒
     */
    public int getTimeToLive() {
        return timeToLive;
    }

    /**
     * 设置活动失效时间。
     *
     * @param timeToLive 活动失效时间，精确到毫秒
     */
    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    /**
     * 得到闲置失效时间。
     *
     * @return 精确到毫秒
     */
    public int getTimeToIdle() {
        return timeToIdle;
    }

    /**
     * 设置闲置失效时间。
     *
     * @param timeToIdle 闲置失效时间，精确到毫秒
     */
    public void setTimeToIdle(int timeToIdle) {
        this.timeToIdle = timeToIdle;
    }

    /**
     * 得到此缓存元素的击中数。
     *
     * @return <code>long</code>
     */
    public long getHitCount() {
        return hitCount;
    }

    /**
     * 设置此缓存元素的击中数。
     *
     * @param hitCount <code>long</code>
     */
    public void setHitCount(long hitCount) {
        this.hitCount = hitCount;
    }

    /**
     * 得到此缓存元素最后一次更新时间。
     *
     * @return 更新时间精确到毫秒
     */
    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * 设置此缓存元素的最后一次更新时间。
     *
     * @param lastUpdateTime 缓存元素的更新时间，精确到毫秒
     */
    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
