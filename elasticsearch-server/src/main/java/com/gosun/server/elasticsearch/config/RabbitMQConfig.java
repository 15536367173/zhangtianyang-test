package com.gosun.server.elasticsearch.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 10:50
 */
@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue queue(){
        return new  Queue("gw_log");
    }
}
