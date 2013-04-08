package com.googlecode.easyec.cache.ehcache.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Ehcache命名空间处理器类。
 *
 * @author JunJie
 */
public class EhcacheServiceNamespaceHandler extends NamespaceHandlerSupport {

    public void init() {
        registerBeanDefinitionParser("default", new DefaultCacheServiceBeanDefinitionParser());
        registerBeanDefinitionParser("noOpCache", new NoOpCacheServiceBeanDefinitionParser());
    }
}
