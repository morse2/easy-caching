package com.googlecode.easyec.cache.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 缓存框架注解切入点拦截器类XML文件配置处理类。
 *
 * @author JunJie
 */
public class CacheAspectNamespaceHandler extends NamespaceHandlerSupport {

    public void init() {
        registerBeanDefinitionParser("aspectj-config", new DefaultCacheAspectBeanDefinitionParser());
    }
}
