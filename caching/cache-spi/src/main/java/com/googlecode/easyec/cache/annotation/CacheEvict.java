package com.googlecode.easyec.cache.annotation;

import java.lang.annotation.*;

/**
 * 缓存失效注解类。
 *
 * @author JunJie
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheEvict {

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
     * 指定缓存清除操作是否在方法被调用之前执行。
     * 默认为false，表示方法调用成功后，才执行清除缓存的操作。
     *
     * @return 布尔值
     */
    boolean beforeInvocation() default false;
}
