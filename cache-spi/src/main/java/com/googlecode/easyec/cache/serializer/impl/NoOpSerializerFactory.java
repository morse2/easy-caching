package com.googlecode.easyec.cache.serializer.impl;

import com.googlecode.easyec.cache.serializer.SerializationException;
import com.googlecode.easyec.cache.serializer.SerializerFactory;

/**
 * 默认无操作的序列化工厂类的实现。
 * 缓存框架默认使用。
 *
 * @author JunJie
 */
public class NoOpSerializerFactory implements SerializerFactory<Object> {

    public Object writeObject(Object o) throws SerializationException {
        return o;
    }

    public Object readObject(Object p) throws SerializationException {
        return p;
    }
}
