package com.googlecode.easyec.cache;

/**
 * 缓存操作异常类。当操作缓存时，发生错误，都抛出此异常。
 * <p>
 * 操作异常情况有：
 * <ol>
 * <li>向缓存中放入值</li>
 * <li>从缓存中读取值</li>
 * <li>从缓存中删除值</li>
 * </ol>
 * </p>
 *
 * @author JunJie
 */
public class CacheException extends Exception {

    private static final long serialVersionUID = -5957823018737043337L;

    public CacheException() {
    }

    public CacheException(String message) {
        super(message);
    }

    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheException(Throwable cause) {
        super(cause);
    }
}
