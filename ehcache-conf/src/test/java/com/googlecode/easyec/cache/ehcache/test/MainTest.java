package com.googlecode.easyec.cache.ehcache.test;

import com.googlecode.caching.service.service.MyService;
import com.googlecode.easyec.cache.CacheService;
import com.googlecode.easyec.cache.util.CacheHelper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * @author JunJie
 */
@ContextConfiguration(locations = "classpath:spring/test/applicationContext-*.xml")
public class MainTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private CacheService cacheService;
    @Autowired
    private MyService myService;

    @Test
    public void pushIntoCache() {
        Assert.assertNotNull(cacheService);

        cacheService.put("a", "123");
        cacheService.put("b", "456");
        cacheService.put("c", "789");

        Assert.assertEquals(cacheService.get("a"), "123");
    }

    @Test
    public void print0() {
        String s1 = myService.print0("2");
        String s2 = myService.print1("2");
        String s3 = myService.print0("2");
    }

    @Test
    public void readAndWrite() throws Exception {
        CacheKey ck = new CacheKey();
        ck.setId("Hello");

        byte[] bs = CacheHelper.writeObjectToBytes(ck);
        Object o = CacheHelper.readObjectFromBytes(bs);
        System.out.println(o);
    }

    @Test
    public void addCache() throws Exception {
        boolean b1 = cacheService.addCacheIfAbsent("MyCache1");
        Assert.assertTrue(b1);
        b1 = cacheService.addCacheIfAbsent("MyCache1");
        Assert.assertFalse(b1);
    }
}
