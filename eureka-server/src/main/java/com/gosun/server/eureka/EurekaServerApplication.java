package com.gosun.server.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 11:16
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public  static  void  main(String[]args){
        SpringApplication.run(EurekaServerApplication.class,args
        );
    }
}
