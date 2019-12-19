package com.gosun.test;

import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;

import javax.annotation.Resource;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/19
 * @Time: 15:58
 */
public class TestSpring {
    @Resource
    private AmqpTemplate amqpTemplate;

    @Test
    public void testSend(){
        String msg = "hello,spring boot amqp";
        this.amqpTemplate.convertAndSend("spring.test.exchange","a.b.c",msg);
    }
}
