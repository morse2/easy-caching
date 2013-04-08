package com.googlecode.easyec.cache.ehcache.config;

import com.googlecode.easyec.cache.ehcache.factory.EhcacheCacheServiceFactoryBean;
import com.googlecode.easyec.cache.ehcache.internal.NoOpCacheProvider;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.rootBeanDefinition;

/**
 * 默认不做缓存的bean定义解析器类。
 *
 * @author JunJie
 */
class NoOpCacheServiceBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class getBeanClass(Element element) {
        return EhcacheCacheServiceFactoryBean.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        builder.addPropertyValue("cacheProvider", rootBeanDefinition(NoOpCacheProvider.class).getBeanDefinition());
    }
}
