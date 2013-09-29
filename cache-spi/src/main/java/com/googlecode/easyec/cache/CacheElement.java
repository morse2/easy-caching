package com.googlecode.easyec.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.*;

/**
 * 缓存元素类。
 *
 * @author JunJie
 * @see Builder
 */
public abstract class CacheElement implements Serializable {

    private static final long serialVersionUID = 4346806366066716022L;
    protected Object key;
    protected Object value;
    private   int    timeToLive;
    private   int    timeToIdle;

    // transient fields
    private transient long hitCount;
    private transient long lastUpdateTime;

    /**
     * 构造方法。
     *
     * @param key   缓存元素的键
     * @param value 缓存元素的值
     */
    private CacheElement(Object key, Object value) {
        this.key = key;
        this.value = value;
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
     * 得到闲置失效时间。
     *
     * @return 精确到毫秒
     */
    public int getTimeToIdle() {
        return timeToIdle;
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
     * 得到此缓存元素最后一次更新时间。
     *
     * @return 更新时间精确到毫秒
     */
    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * 缓存元素构建器类
     *
     * @author JunJie
     */
    public static class Builder implements Externalizable {

        private static final Logger logger = LoggerFactory.getLogger(Builder.class);
        private static final long serialVersionUID = -4190508889679361464L;
        private CacheElement element;

        /**
         * 构造方法。
         */
        public Builder() {
            createDefaultCacheElement(null, null);
        }

        /**
         * 构造方法
         *
         * @param k 缓存的键
         * @param v 缓存的值
         */
        public Builder(Object k, Object v) {
            createDefaultCacheElement(k, v);
        }

        /**
         * 设置此缓存的键信息
         *
         * @param key 缓存的键
         * @return 缓存的构建器对象
         */
        public Builder key(Object key) {
            this.element.key = key;
            return this;
        }

        /**
         * 设置此缓存的值信息
         *
         * @param value 缓存的值
         * @return 缓存的构建器对象
         */
        public Builder value(Object value) {
            this.element.value = value;
            return this;
        }

        /**
         * 设置此缓存值的活动失效时间
         *
         * @param timeToLive 活动失效时间
         * @return 缓存的构建器对象
         */
        public Builder timeToLive(int timeToLive) {
            if (timeToLive > 0) this.element.timeToLive = timeToLive;
            return this;
        }

        /**
         * 设置此缓存值的闲置失效时间
         *
         * @param timeToIdle 闲置失效时间
         * @return 缓存的构建器对象
         */
        public Builder timeToIdle(int timeToIdle) {
            if (timeToIdle > 0) this.element.timeToIdle = timeToIdle;
            return this;
        }

        /**
         * 设置此缓存的击中数
         *
         * @param hitCount 击中数
         * @return 缓存的构建器对象
         */
        public Builder hitCount(long hitCount) {
            this.element.hitCount = hitCount;
            return this;
        }

        /**
         * 设置此缓存的最后更新时间
         *
         * @param lastUpdateTime 最后更新时间
         * @return 缓存的构建器对象
         */
        public Builder lastUpdateTime(long lastUpdateTime) {
            this.element.lastUpdateTime = lastUpdateTime;
            return this;
        }

        /**
         * 执行构建方法。
         * 此方法返回缓存元素对象实例
         *
         * @return <code>CacheElement</code>实例对象
         */
        public CacheElement build() {
            Assert.notNull(element.key, "The key of cache cannot be null.");
            Assert.notNull(element.value, "The value of cache cannot be null.");

            return this.element;
        }

        private void createDefaultCacheElement(Object k, Object v) {
            this.element = new CacheElement(k, v) {

                private static final long serialVersionUID = 7826364481214265856L;
            };
        }

        public void writeExternal(ObjectOutput out) throws IOException {
            logger.trace("Serialize the cache object. The cache info is below: {");
            logger.trace("\tCache key: [{}].", element.key);
            logger.trace("\tCache val: [{}].", element.value);
            logger.trace("\tCache's timeToLive: [{}].", element.timeToLive);
            logger.trace("\tCache's timeToIdle: [{}].", element.timeToIdle);
            logger.trace("}. End of cache info.");

            out.writeObject(element.key);
            out.writeObject(element.value);
            out.writeInt(element.timeToLive);
            out.writeInt(element.timeToIdle);
        }

        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            Object k = in.readObject();
            Object v = in.readObject();
            int timeToLive = in.readInt();
            int timeToIdle = in.readInt();

            logger.trace("Deserializae the cache object. The cache info is below: {");
            logger.trace("\tCache key: [{}].", k);
            logger.trace("\tCache val: [{}].", v);
            logger.trace("\tCache's timeToLive: [{}].", timeToLive);
            logger.trace("\tCache's timeToIdle: [{}].", timeToIdle);
            logger.trace("}. End of cache info.");

            createDefaultCacheElement(k, v);
            this.element.timeToLive = timeToLive;
            this.element.timeToIdle = timeToIdle;
        }
    }
}
