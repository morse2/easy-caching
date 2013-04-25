package com.googlecode.easyec.cache.serializer;

/**
 * 序列化对象异常类。
 *
 * @author JunJie
 */
public class SerializationException extends Exception {

    private static final long serialVersionUID = 5080412657326334321L;

    public SerializationException(String message) {
        super(message);
    }

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializationException(Throwable cause) {
        super(cause);
    }
}
