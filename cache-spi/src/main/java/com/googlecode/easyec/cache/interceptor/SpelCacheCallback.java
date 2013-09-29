package com.googlecode.easyec.cache.interceptor;

import com.googlecode.easyec.cache.CacheElement;
import org.springframework.expression.EvaluationContext;

import java.lang.reflect.Method;

import static org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode;

/**
 * Spring EL缓存操作回调类。
 *
 * @author JunJie
 */
public abstract class SpelCacheCallback {

    private final ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator();
    private boolean beforeInvocation;
    private String  caches[];
    private String  key;

    protected SpelCacheCallback(String[] caches, String key) {
        this.caches = caches;
        this.key = key;
    }

    protected SpelCacheCallback(boolean beforeInvocation, String[] caches, String key) {
        this.beforeInvocation = beforeInvocation;
        this.caches = caches;
        this.key = key;
    }

    public boolean isBeforeInvocation() {
        return beforeInvocation;
    }

    public String[] getCaches() {
        return caches;
    }

    public String getKey() {
        return key;
    }

    /**
     * 解析缓存的KEY值。
     *
     * @param args 当前被调用方法的参数
     * @return 缓存的KEY值
     */
    protected String parseCacheKey(Method method, Object[] args, Object target, Class<?> targetClass) {
        EvaluationContext ctx = expressionEvaluator.createEvaluationContext(method, args, target, targetClass);

        Object v = expressionEvaluator.key(key, method, ctx);

        if (null != v) {
            return String.valueOf(reflectionHashCode(v));
        }

        return null;
    }

    /**
     * 创建缓存元素类。用于存放至缓存中。
     *
     * @param key        缓存元素的KEY
     * @param value      缓存元素的内容
     * @param timeToLive 活动失效时间
     * @param timeToIdle 闲置失效时间
     * @return <code>CacheElement</code>实例对象
     */
    protected CacheElement createCacheElement(Object key, Object value, int timeToLive, int timeToIdle) {
        return new CacheElement.Builder(key, value)
            .timeToIdle(timeToIdle * 1000)
            .timeToLive(timeToLive * 1000)
            .build();
    }

    /**
     * 执行缓存业务逻辑的方法。
     *
     * @return 执行缓存逻辑后返回的内容
     * @throws Throwable
     */
    abstract public Object execute() throws Throwable;
}
