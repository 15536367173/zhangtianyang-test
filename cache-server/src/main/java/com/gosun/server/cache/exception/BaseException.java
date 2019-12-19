package com.gosun.server.cache.exception;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 11:54
 */
public class BaseException  extends  RuntimeException{

    public  String msg;
    public  String code;

    public  BaseException(){
        super();
    }
    public  BaseException(String msg){
        super(msg);
        this.msg = msg;
    }

    public  BaseException(String msg,String code){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public String toString() {
        return "BaseException{code='" + this.code + '\'' + ", msg='" + this.msg + '\'' + '}';
    }



}
