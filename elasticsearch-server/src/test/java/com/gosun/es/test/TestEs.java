package com.gosun.es.test;

import com.alibaba.fastjson.JSON;
import com.gosun.server.elasticsearch.ElasticSearchApplication;
import com.gosun.server.elasticsearch.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 10:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticSearchApplication.class)
public class TestEs {

    @Resource
    private SearchService searchService;

    @Test
    public  void test() throws IOException {


        System.out.println(searchService.existsIndex());

////
//        System.out.println(searchService.existsIndex());
//
        // searchService.delIndex();
      //  searchService.createIndex();

//
//        System.out.println(searchService.existsIndex());

        //appkey,servIP,venderId,remoteIp,apiName,platformRepTime
        // ,requestContent,errorCode,receiveTime,createTime
        for (int i = 0; i < 10; i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("appKey","meidi");
            map.put("servIP","192.168.62."+(i+1)*3);
            map.put("remoteIp","192.128.32."+(i+1)*3);
            map.put("venderId",1);
            map.put("apiName","jingdong.order.cancel");
            map.put("platformRepTime",300*i);
            map.put("requestContent","美的电磁炉");
            map.put("receiveTime",new Date());
            map.put("createTime",new Date());
            map.put("errorCode","0");

            String  json = JSON.toJSONString(map);

            //searchService.addLog(json);

        }

        // searchService.queryAll();
        Map<String,Object> map = new HashMap<>();
        //  map.put("appKey","561AC1A8676CFCB0CC61B041AE42ABB8");
        map.put("apiName","jingdong.goods.find");
        // map.put("requestContent","海尔");
//        map.put("start",1573701950470l);
//        map.put("end",19973701950470l);

        //requestContent

//        map.put("startIndex",0);
//        map.put("rows",20);
//
//        String  params = JSON.toJSONString(map);
//
//        System.err.println("total:"+searchService.searchLogCount(params));
//        System.err.println("result list"+searchService.searchLog(params));


        System.out.println("apiName and avg:"+searchService.statAvg(1573701950400l,9999999999999999l));



    }

}