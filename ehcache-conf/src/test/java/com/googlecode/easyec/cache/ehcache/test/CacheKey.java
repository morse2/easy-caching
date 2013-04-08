package com.googlecode.easyec.cache.ehcache.test;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-21
 * Time: 下午4:01
 * To change this template use File | Settings | File Templates.
 */
public class CacheKey implements Serializable {

    public static final String MY_KEY = "myKey$";
    private static final long serialVersionUID = -2149017501318017281L;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
