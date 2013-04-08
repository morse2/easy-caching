package com.googlecode.easyec.cache.interceptor;

/**
 * 缓存回调接口。
 * 实现此类，可以在实现完逻辑后。
 * 将结果添加至指定的缓存区域中。
 *
 * @author JunJie
 */
public interface CacheCallback<T> {

    /**
     * 业务逻辑方法。
     * 此方法会在特定条件下，触发业务操作。
     *
     * @return {@link T} 泛型
     * @throws Exception 错误异常
     */
    T populate() throws Exception;

    /**
     * 缓存指定的Key
     *
     * @return 缓存的键，任何Java类型
     */
    Object getKey();

    /**
     * 返回缓存区名字。
     * 在业务逻辑方法执行成功后，
     * 根据返回条件，存入此方法返回的缓存区名的缓存区中。
     *
     * @return
     */
    String[] getCaches();
}
