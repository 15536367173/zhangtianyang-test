package com.gosun.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/19
 * @Time: 15:53
 */

@Component
public class Listener {
    // RabbitListener 方法上的注解，声明这个方法是一个消费者方法，需要指定下面的属性，bindings：指定绑定关系，可以有多个， 值是@QueueBinding的数组
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "spring.test.queue",durable = "true"),
            exchange = @Exchange(value = "spring.test.exchange",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key={"#.#"}))
    public  void listen(String msg){
        System.out.println("接收到消息："+msg);
    }
}
