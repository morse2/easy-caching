package com.googlecode.easyec.cache.serializer.impl;

import com.googlecode.easyec.cache.serializer.SerializationException;
import com.googlecode.easyec.cache.serializer.SerializerFactory;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static org.apache.commons.lang.ArrayUtils.isEmpty;

/**
 * 字节数组序列号工厂实现类
 *
 * @author JunJie
 */
public class ByteArraySerializerFactory implements SerializerFactory<byte[]> {

    private static final Logger logger = LoggerFactory.getLogger(ByteArraySerializerFactory.class);

    public byte[] writeObject(Object o) throws SerializationException {
        ObjectOutputStream out = null;

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            out = new ObjectOutputStream(bos);
            out.writeObject(o);
            out.flush();

            return bos.toByteArray();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);

            throw new SerializationException(e);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    public Object readObject(byte[] p) throws SerializationException {
        if (isEmpty(p)) return null;

        ObjectInputStream in = null;

        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(p);
            in = new ObjectInputStream(bis);
            return in.readObject();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new SerializationException(e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
}
