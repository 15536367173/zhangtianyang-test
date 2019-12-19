package com.gosun.server.gateway.exception;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 11:37
 */

public class BaseException extends RuntimeException {
    protected String code;
    protected String msg;

    public BaseException() {
    }

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public BaseException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String toString() {
        return "BaseException{code='" + this.code + '\'' + ", msg='" + this.msg + '\'' + '}';
    }
}
