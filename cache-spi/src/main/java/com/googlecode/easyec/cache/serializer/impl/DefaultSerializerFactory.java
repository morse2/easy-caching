package com.googlecode.easyec.cache.serializer.impl;

import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.googlecode.easyec.cache.serializer.SerializationException;
import com.googlecode.easyec.cache.serializer.SerializerFactory;
import com.googlecode.easyec.cache.serializer.kryo.KryoSerializerFactory;
import org.objenesis.strategy.StdInstantiatorStrategy;

/**
 * 缓存框架默认使用的对象序列化工厂的实现类。
 *
 * @author JunJie
 */
public class DefaultSerializerFactory implements SerializerFactory {

    private static final ThreadLocal<SerializerFactory> _t = new ThreadLocal<SerializerFactory>();

    public byte[] writeObject(Object o) throws SerializationException {
        return getSerializerFactory().writeObject(o);
    }

    public Object readObject(byte[] bs) throws SerializationException {
        return getSerializerFactory().readObject(bs);
    }

    private SerializerFactory getSerializerFactory() {
        synchronized (_t) {
            SerializerFactory serializerFactory = _t.get();

            if (null == serializerFactory) {
                serializerFactory = new KryoSerializerFactory();

                ((KryoSerializerFactory) serializerFactory).setInstantiatorStrategy(new StdInstantiatorStrategy());
                ((KryoSerializerFactory) serializerFactory).setDefaultSerializer(JavaSerializer.class);

                _t.set(serializerFactory);
            }

            return serializerFactory;
        }
    }
}
