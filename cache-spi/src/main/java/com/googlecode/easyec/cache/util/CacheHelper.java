package com.googlecode.easyec.cache.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 缓存帮助类。
 *
 * @author JunJie
 */
public class CacheHelper {

    private static final ThreadLocal<Kryo> _t = new ThreadLocal<Kryo>();
    private static final int BUFFER_SIZE = 256;

    private static Kryo getKryo() {
        synchronized (_t) {
            Kryo kryo = _t.get();

            if (null == kryo) {
                kryo = new Kryo();

                _t.set(kryo);
            }

            return kryo;
        }
    }

    /**
     * 将字节码转换成对象信息。如果字节码对象为null，则此方法返回null。
     *
     * @param bs 字节码对象
     * @return 从字节码中转换出来的对象
     * @throws Exception 转换错误异常
     */
    public static Object readObjectFromBytes(byte[] bs) throws Exception {
        if (bs != null) {
            Input in = new Input(new ByteArrayInputStream(bs), BUFFER_SIZE);

            try {
                return getKryo().readClassAndObject(in);
            } finally {
                in.close();
            }
        }

        return null;
    }

    /**
     * 将对象写入到字节码中，并将其返回。如果对象为null，则返回null。
     *
     * @param o 对象实例
     * @return 字节码对象
     * @throws Exception 转换错误异常
     */
    public static byte[] writeObjectToBytes(Object o) throws Exception {
        if (o != null) {
            Output out = new Output(new ByteArrayOutputStream(), BUFFER_SIZE);
            try {
                getKryo().writeClassAndObject(out, o);

                return out.toBytes();
            } finally {
                out.close();
            }
        }

        return null;
    }

    /**
     * 深度拷贝对象。
     *
     * @param o   要被拷贝的对象
     * @param <T> 泛型对象
     * @return 对象副本
     */
    public static <T> T copy(T o) {
        return getKryo().copy(o);
    }

    /**
     * 浅拷贝对象。
     *
     * @param o   要被拷贝的对象
     * @param <T> 泛型对象
     * @return 对象副本
     */
    public static <T> T copyShallow(T o) {
        return getKryo().copyShallow(o);
    }
}
