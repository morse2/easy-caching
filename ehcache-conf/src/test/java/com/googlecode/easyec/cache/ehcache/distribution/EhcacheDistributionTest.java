package com.googlecode.easyec.cache.ehcache.distribution;

import com.googlecode.easyec.cache.CacheService;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by JunJie on 2015/2/22.
 */
public class EhcacheDistributionTest {

    private CacheService cacheService1;
    private CacheService cacheService2;

    @Before
    public void setup() {
        ApplicationContext ctx1 = new ClassPathXmlApplicationContext("spring/test/applicationContext-distribution1.xml");
        ApplicationContext ctx2 = new ClassPathXmlApplicationContext("spring/test/applicationContext-distribution2.xml");

        cacheService1 = ctx1.getBean(CacheService.class);
        cacheService2 = ctx2.getBean(CacheService.class);
    }

    @Test
    public void synchronizeCache() {
        for (int i = 0; i < 100; i++) {
            boolean b = cacheService1.put("key" + i, "ABC");
            Assert.assertTrue(b);

            Object val = cacheService2.get("key" + i);
            Assert.assertNotNull(val);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        EhcacheDistributionTest test = new EhcacheDistributionTest();
        test.setup();
        Thread.sleep(10000);
        test.synchronizeCache();
    }
}
