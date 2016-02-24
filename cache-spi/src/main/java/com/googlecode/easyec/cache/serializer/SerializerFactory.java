package com.googlecode.easyec.cache.serializer;

/**
 * 序列化工厂类。
 * <p>此类定义了序列化对象的一般方法。</p>
 *
 * @author JunJie
 */
public interface SerializerFactory<T> {

    /**
     * 将对象序列化成指定的数据格式
     *
     * @param o 要被序列化的对象
     * @return 序列化后的对象类型
     * @throws SerializationException 序列化异常信息
     */
    T writeObject(Object o) throws SerializationException;

    /**
     * 读取从缓存传递过来的数据格式，并反序列化成对象
     *
     * @param p 参数类型
     * @return 反序列化出来的对象实例
     * @throws SerializationException 序列化异常信息
     */
    Object readObject(T p) throws SerializationException;
}
