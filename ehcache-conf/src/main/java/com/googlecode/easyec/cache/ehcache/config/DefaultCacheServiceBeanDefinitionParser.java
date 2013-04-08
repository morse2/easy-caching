package com.googlecode.easyec.cache.ehcache.config;

import com.googlecode.easyec.cache.ehcache.factory.EhcacheCacheServiceFactoryBean;
import com.googlecode.easyec.cache.ehcache.internal.EhcacheDefaultCacheProvider;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.rootBeanDefinition;

/**
 * 默认本地安全的缓存服务Bean定义解析器类。
 *
 * @author JunJie
 */
class DefaultCacheServiceBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder bean) {
        BeanDefinitionBuilder cacheManagerFactory = rootBeanDefinition(EhCacheManagerFactoryBean.class);

        String configLocation = element.getAttribute("configLocation");
        if (!StringUtils.hasText(configLocation)) {
            throw new IllegalArgumentException("Property configLocation cannot be null.");
        }

        cacheManagerFactory.addPropertyValue("configLocation", configLocation);

        String shared = element.getAttribute("shared");
        if (Boolean.parseBoolean(shared)) {
            cacheManagerFactory.addPropertyValue("shared", true);
        }

        String cacheManagerName = element.getAttribute("cacheManagerName");
        if (StringUtils.hasText(cacheManagerName)) {
            cacheManagerFactory.addPropertyValue("cacheManagerName", cacheManagerName);
        }

        BeanDefinitionBuilder cacheProvider = rootBeanDefinition(EhcacheDefaultCacheProvider.class);
        cacheProvider.addConstructorArgValue(cacheManagerFactory.getBeanDefinition());

        bean.addPropertyValue("cacheProvider", cacheProvider.getBeanDefinition());
    }

    @Override
    protected Class getBeanClass(Element element) {
        return EhcacheCacheServiceFactoryBean.class;
    }
}
