package com.gosun.server.elasticsearch.listener;


import com.gosun.server.elasticsearch.service.SearchService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 10:51
 */
@Component
public class MessageListener {

    @Resource
    private SearchService searchService;

    @RabbitListener(queues = "gw_log")
    public  void  onMessage(String msg){
        System.err.println("======>"+msg);
        //处理业务逻辑逻辑
        //日志
        //把消息添加到es中
        try {
            searchService.addLog(msg);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}

