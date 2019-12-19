package com.gosun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/19
 * @Time: 15:57
 */
@SpringBootApplication
@Configuration
public class SpringMQApplication {
    public static void main(String[]args){
        SpringApplication.run(SpringMQApplication.class,args);
    }
}
