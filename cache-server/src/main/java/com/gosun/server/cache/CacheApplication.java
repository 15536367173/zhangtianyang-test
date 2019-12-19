package com.gosun.server.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 11:53
 */
@SpringBootApplication
@EnableEurekaClient
public class CacheApplication {

    public  static  void  main(String[]args){

        SpringApplication.run(CacheApplication.class,args);

    }

}
