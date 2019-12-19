package com.gosun.server.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 12:05
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy //开启网关
@EnableFeignClients //开启feign组件
public class GatewayApplication {

    public  static  void  main(String[]args){
        SpringApplication.run(GatewayApplication.class,args);
    }

}