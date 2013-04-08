package com.googlecode.easyec.cache.rest.jaxb;

import com.googlecode.easyec.cache.util.CacheHelper;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-8-6
 * Time: 下午5:26
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CacheObject {

    @XmlAttribute(name = "cacheName")
    private String cacheName;
    @XmlAttribute(name = "key")
    private String key;
    @XmlMimeType(MediaType.APPLICATION_OCTET_STREAM)
    private byte[] ser;

    protected CacheObject() {
        // no op
    }

    public CacheObject(String key, Object v) throws Exception {
        this(null, key, v);
    }

    public CacheObject(String cacheName, String key, Object v) throws Exception {
        this.cacheName = cacheName;
        this.key = key;

        if (v != null) {
            if (v instanceof byte[]) {
                this.ser = (byte[]) v;
            } else {
                this.ser = CacheHelper.writeObjectToBytes(v);
            }
        }
    }

    public String getCacheName() {
        return cacheName;
    }

    public Object getKey() {
        return key;
    }

    public byte[] getSer() {
        return ser;
    }
}
