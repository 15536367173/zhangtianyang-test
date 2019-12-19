package com.gosun.server.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 11:36
 */
@Configuration
public class RabbitMQConfig {

    @Bean //fanout  topic  direct
    public Queue queue(){
        return  new Queue("gw_log");
    }
}
