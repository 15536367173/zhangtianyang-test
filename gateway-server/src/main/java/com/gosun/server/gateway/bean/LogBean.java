package com.gosun.server.gateway.bean;

import lombok.Data;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 11:35
 */
@Data
public class LogBean {
    //appkey
    private String appkey;//应用KEY,也就是商家在开放平台的id
    private String servIP;//服务IP,网关的IP
    private String venderId;//商家ID ,商家在电商平台的id
    private String remoteIp;//来源IP
    private String apiName;//API名称,请求都api接口
    private long totalRepTime;//总响应时间
    private long platformRepTime;//响应时间
    private String requestContent;//请求包信息
    private String errorCode;//错误码
    private long receiveTime;//接收时间
    private  long createTime;

}
