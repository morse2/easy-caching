package com.googlecode.easyec.cache.interceptor;

import com.googlecode.easyec.cache.CacheElement;
import com.googlecode.easyec.cache.CacheService;
import com.googlecode.easyec.cache.annotation.CacheEvict;
import com.googlecode.easyec.cache.annotation.CachePut;
import com.googlecode.easyec.cache.annotation.Cacheable;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author JunJie
 */
@Aspect
public class CacheAspectInterceptor implements Ordered {

    protected static final String aspectAround
            = "@annotation(com.googlecode.easyec.cache.annotation.Cacheable)" +
            "||@annotation(com.googlecode.easyec.cache.annotation.CachePut)" +
            "||@annotation(com.googlecode.easyec.cache.annotation.CacheEvict)";

    private CacheService cacheService;
    private int order;

    @Autowired(required = false)
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    /**
     * 获取当前系统配置中的缓存服务对象实例
     *
     * @return <code>CacheService</code>
     */
    public CacheService getCacheService() {
        return cacheService;
    }

    @Around(aspectAround)
    public Object doWithCache(ProceedingJoinPoint joinPoint) throws Throwable {
        if (null == cacheService) return joinPoint.proceed(joinPoint.getArgs());

        Object target = joinPoint.getTarget();

        Class<?> targetClass = AopUtils.getTargetClass(target);
        if (null == targetClass) {
            targetClass = target.getClass();
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = AopUtils.getMostSpecificMethod(signature.getMethod(), targetClass);

        if (null == method) {
            method = signature.getMethod();
        }

        CacheEvict cacheEvict = findCacheAnnotation(CacheEvict.class, method, targetClass);
        if (null != cacheEvict) {
            return createCacheEvictCallback(cacheEvict, joinPoint, method, targetClass).execute();
        }

        CachePut cachePut = findCacheAnnotation(CachePut.class, method, targetClass);
        if (null != cachePut) {
            return createCachePutCallback(cachePut, joinPoint, method, targetClass).execute();
        }

        Cacheable cacheable = findCacheAnnotation(Cacheable.class, method, targetClass);
        if (null != cacheable) {
            return createCacheableCallback(cacheable, joinPoint, method, targetClass).execute();
        }

        return joinPoint.proceed(joinPoint.getArgs());
    }

    protected <T extends Annotation> T findCacheAnnotation(Class<T> cls, Method method, Class<?> targetClass) {
        T a = method.getAnnotation(cls);
        if (null != a) return a;

        a = targetClass.getAnnotation(cls);
        if (null != a) return a;

        Method m = ReflectionUtils.findMethod(
                targetClass.getClass(),
                method.getName(),
                method.getParameterTypes()
        );

        return null != m ? m.getAnnotation(cls) : null;
    }

    /**
     * 创建一个{@link Cacheable}注解解析的缓存回调类。
     *
     * @param cacheable <code>Cacheable</code>注解对象
     * @param jp        {@link ProceedingJoinPoint}实例对象
     * @return <code>SpelCacheCallback</code>实例对象
     */
    protected SpelCacheCallback createCacheableCallback(
            final Cacheable cacheable, final ProceedingJoinPoint jp, final Method method, final Class<?> targetClass
    ) {
        return new SpelCacheCallback(cacheable.value(), cacheable.key()) {

            @Override
            public Object execute() throws Throwable {
                // 判断缓存列表是否为空，不为空则操作缓存
                if (ArrayUtils.isNotEmpty(getCaches())) {
                    // 解析当前缓存的KEY
                    String k = this.parseCacheKey(method, jp.getArgs(), jp.getTarget(), targetClass);
                    // 当前缓存的KEY不为空，则继续操作缓存
                    if (StringUtils.isNotBlank(k)) {
                        for (String name : getCaches()) {
                            Object o = cacheService.get(name, k);

                            // 如果从缓存中获取的对象不为空，
                            // 则直接返回此对象
                            if (null != o) {
                                if (!(o instanceof CacheElement)) return o;
                                return ((CacheElement) o).getValue();
                            }
                        }

                        Object o = jp.proceed(jp.getArgs());

                        if (null != o) {
                            CacheElement e = createCacheElement(k, o, cacheable.timeToLive(), cacheable.timeToIdle());

                            for (String name : getCaches()) {
                                cacheService.put(name, k, e);
                            }
                        }

                        return o;
                    }
                }

                return jp.proceed(jp.getArgs());
            }
        };
    }

    /**
     * 创建一个{@link CacheEvict}注解解析的缓存回调类。
     *
     * @param cacheEvict <code>CacheEvict</code>注解对象
     * @param jp         {@link ProceedingJoinPoint}实例对象
     * @return {@link SpelCacheCallback}实例对象
     */
    protected SpelCacheCallback createCacheEvictCallback(
            final CacheEvict cacheEvict, final ProceedingJoinPoint jp, final Method method, final Class<?> targetClass
    ) {
        return new SpelCacheCallback(cacheEvict.beforeInvocation(), cacheEvict.value(), cacheEvict.key()) {

            @Override
            public Object execute() throws Throwable {
                // 判断缓存列表是否为空，不为空则操作缓存
                if (ArrayUtils.isNotEmpty(getCaches())) {
                    // 解析当前缓存的KEY
                    String k = this.parseCacheKey(method, jp.getArgs(), jp.getTarget(), targetClass);
                    // 当前缓存的KEY不为空，则继续操作缓存
                    if (StringUtils.isNotBlank(k)) {
                        if (isBeforeInvocation()) {
                            for (String name : getCaches()) {
                                cacheService.removeCache(name, k);
                            }
                        }

                        Object o = jp.proceed(jp.getArgs());

                        if (!isBeforeInvocation()) {
                            for (String name : getCaches()) {
                                cacheService.removeCache(name, k);
                            }
                        }

                        return o;
                    }
                }

                return jp.proceed(jp.getArgs());
            }
        };
    }

    /**
     * 创建一个{@link CachePut}注解解析的缓存回调类。
     *
     * @param cachePut <code>CachePut</code>注解对象
     * @param jp       {@link ProceedingJoinPoint}实例对象
     * @return {@link SpelCacheCallback}实例对象
     */
    protected SpelCacheCallback createCachePutCallback(
            final CachePut cachePut, final ProceedingJoinPoint jp, final Method method, final Class<?> targetClass
    ) {
        return new SpelCacheCallback(cachePut.value(), cachePut.key()) {

            @Override
            public Object execute() throws Throwable {
                // 判断缓存列表是否为空，不为空则操作缓存
                if (ArrayUtils.isNotEmpty(getCaches())) {
                    // 解析当前缓存的KEY
                    String k = this.parseCacheKey(method, jp.getArgs(), jp.getTarget(), targetClass);
                    // 当前缓存的KEY不为空，则继续操作缓存
                    if (StringUtils.isNotBlank(k)) {
                        Object o = jp.proceed(jp.getArgs());

                        if (null != o) {
                            CacheElement e = createCacheElement(k, o, cachePut.timeToLive(), cachePut.timeToIdle());

                            for (String name : getCaches()) {
                                cacheService.put(name, k, e);
                            }
                        }

                        return o;
                    }
                }

                return jp.proceed(jp.getArgs());
            }
        };
    }

    /**
     * 设置拦截执行的顺序。
     *
     * @param order 顺序值
     */
    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
