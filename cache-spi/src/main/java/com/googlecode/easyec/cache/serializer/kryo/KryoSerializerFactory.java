package com.googlecode.easyec.cache.serializer.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.googlecode.easyec.cache.serializer.SerializationException;
import com.googlecode.easyec.cache.serializer.SerializerFactory;
import org.apache.commons.io.IOUtils;
import org.objenesis.strategy.InstantiatorStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.Set;

/**
 * 对象序列化工厂的Kryo的实现类。
 * <p>此类基于<code>Kryo</code>开源框架实现了对象序列化操作。</p>
 *
 * @author JunJie
 */
public class KryoSerializerFactory implements SerializerFactory {

    private static final Logger logger = LoggerFactory.getLogger(KryoSerializerFactory.class);
    private Map<Class<?>, Serializer<?>> defaultSerializers;
    private Class<? extends Serializer> defaultSerializer;
    private InstantiatorStrategy instantiatorStrategy;

    /**
     * 为当前Kryo设置一个默认的根序列化处理器对象
     *
     * @param defaultSerializer 全局默认的序列化处理器对象
     */
    public void setDefaultSerializer(Class<? extends Serializer> defaultSerializer) {
        this.defaultSerializer = defaultSerializer;
    }

    /**
     * 为Kryo设置实例化策略对象
     *
     * @param instantiatorStrategy <code>InstantiatorStrategy</code>对象
     */
    public void setInstantiatorStrategy(InstantiatorStrategy instantiatorStrategy) {
        this.instantiatorStrategy = instantiatorStrategy;
    }

    /**
     * 设置一组默认的Kryo序列化处理器类对象
     *
     * @param defaultSerializers <code>Map</code>列表
     */
    public void setDefaultSerializers(Map<Class<?>, Serializer<?>> defaultSerializers) {
        this.defaultSerializers = defaultSerializers;
    }

    public byte[] writeObject(Object o) throws SerializationException {
        Output output = null;

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            output = new Output(out);
            getInstance().writeClassAndObject(output, o);

            output.flush();

            return out.toByteArray();
        } catch (KryoException e) {
            logger.error(e.getMessage(), e);

            throw new SerializationException(e);
        } finally {
            IOUtils.closeQuietly(output);
        }
    }

    public Object readObject(byte[] bs) throws SerializationException {
        Input input = null;

        try {
            input = new Input(bs);

            return getInstance().readClassAndObject(input);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new SerializationException(e);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * 初始化<code>Kryo</code>对象实例的方法
     *
     * @return 返回一个新的Kryo对象
     */
    private synchronized Kryo getInstance() {
        Kryo kryo = new Kryo();

        if (null != defaultSerializer) kryo.setDefaultSerializer(defaultSerializer);
        if (null != instantiatorStrategy) kryo.setInstantiatorStrategy(instantiatorStrategy);

        if (null != defaultSerializers && !defaultSerializers.isEmpty()) {
            Set<Class<?>> classes = defaultSerializers.keySet();
            for (Class<?> cls : classes) {
                Serializer<?> serializer = defaultSerializers.get(cls);
                Assert.notNull(serializer, "Default Serializer object is null.");

                kryo.addDefaultSerializer(cls, serializer);
            }
        }

        return kryo;
    }
}
