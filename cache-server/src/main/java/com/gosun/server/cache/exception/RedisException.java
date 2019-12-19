package com.gosun.server.cache.exception;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 11:58
 */
public class RedisException extends  BaseException {
    public RedisException(String code, String msg) {
        super(code,msg);
    }
}
