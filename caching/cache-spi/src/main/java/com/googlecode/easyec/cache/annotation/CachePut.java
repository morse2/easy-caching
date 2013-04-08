package com.googlecode.easyec.cache.annotation;

import java.lang.annotation.*;

/**
 * 缓存设置注解类。
 *
 * @author JunJie
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CachePut {

    /**
     * 缓存名列表。
     * 默认缓存名为"globalCache"。
     *
     * @return 要操作的缓存列表
     */
    String[] value() default { "globalCache" };

    /**
     * 当前缓存的KEY值。
     * 必须遵循Spring EL语法。
     *
     * @return 缓存KEY值
     */
    String key();

    /**
     * 活动失效时间，精确到秒。
     *
     * @return 小于0，则表示无超时时间
     */
    int timeToLive() default -1;

    /**
     * 闲置失效时间，精确到秒。
     *
     * @return 小于0，则表示无超时时间
     */
    int timeToIdle() default -1;
}
