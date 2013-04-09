package com.googlecode.easyec.cache.util;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 缓存帮助类。
 *
 * @author JunJie
 */
public class CacheHelper {

    private static final int BUFFER_SIZE = 256;

    /**
     * 将字节码转换成对象信息。如果字节码对象为null，则此方法返回null。
     *
     * @param bs 字节码对象
     * @return 从字节码中转换出来的对象
     * @throws Exception 转换错误异常
     */
    public static Object readObjectFromBytes(byte[] bs) throws Exception {
        if (bs != null) {
            ByteArrayInputStream in = new ByteArrayInputStream(bs);
            ObjectInputStream i = new ObjectInputStream(in);

            try {
                return i.readObject();
            } finally {
                IOUtils.closeQuietly(in);
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
            ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
            ObjectOutputStream s = new ObjectOutputStream(out);

            try {
                s.writeObject(o);

                return out.toByteArray();
            } finally {
                IOUtils.closeQuietly(out);
            }
        }

        return null;
    }
}
