package com.googlecode.easyec.cache.rest.jaxb;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.*;

/**
 * RESTful缓存对象实体类。
 * <p>此类用于和RESTful服务端交互时的数据载体</p>
 *
 * @author JunJie
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

    public CacheObject(String key, byte[] ser) throws Exception {
        this(null, key, ser);
    }

    public CacheObject(String cacheName, String key, byte[] ser) throws Exception {
        this.cacheName = cacheName;
        this.key = key;
        this.ser = ser;
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
