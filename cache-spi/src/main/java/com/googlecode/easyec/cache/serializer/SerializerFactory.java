package com.googlecode.easyec.cache.serializer;

/**
 * 序列化工厂类。
 * <p>此类定义了序列化对象的一般方法。</p>
 *
 * @author JunJie
 */
public interface SerializerFactory {

    /**
     * 将对象序列化成二进制数据
     *
     * @param o 要被序列化的对象
     * @return 序列化后的二进制数据
     * @throws SerializationException 序列化异常信息
     */
    byte[] writeObject(Object o) throws SerializationException;

    /**
     * 读取二进制数据，并反序列化成对象
     *
     * @param bs 序列化过的二进制数据
     * @return 反序列化出来的对象实例
     * @throws SerializationException 序列化异常信息
     */
    Object readObject(byte[] bs) throws SerializationException;
}
