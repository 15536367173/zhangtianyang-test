package com.gosun.server.gateway.exception;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 11:37
 */
public class GatewayException extends BaseException {
    public GatewayException(String code, String msg) {
        super(code,msg);
    }
}
