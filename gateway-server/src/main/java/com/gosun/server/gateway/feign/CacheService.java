package com.gosun.server.gateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 11:40
 */

@FeignClient(serviceId = "cache-service",fallback = CacheServiceFallback.class)
public interface CacheService {
    @RequestMapping("/set")
    //RequestParam.value() was empty on parameter 1
    public  void set(@RequestParam("key") String  key, @RequestParam("value")  String value);
    @RequestMapping("/get")
    public  String get( @RequestParam("key") String  key);

    @RequestMapping(value = "/hset",method = RequestMethod.GET)
    public  boolean hset(
            @RequestParam("key") String key,
            @RequestParam("field")String field,
            @RequestParam("value")String value);


    @RequestMapping(value = "/hmset",method = RequestMethod.POST)
    public  boolean hmset( @RequestParam("key") String key,
                           @RequestBody Map<String,Object> map);

    @RequestMapping(value = "/hget",method = RequestMethod.GET)
    public  String hget(@RequestParam("key") String key,
                        @RequestParam("field") String field);


    @RequestMapping(value = "/hgetAll",method = RequestMethod.GET)
    public  Map<String,Object> hGetAll(@RequestParam("key") String key);


    @RequestMapping(value = "/del",method = RequestMethod.GET)
    public  boolean del(@RequestParam("key") String key);

    @RequestMapping(value = "/incr",method = RequestMethod.GET)
    public long  incr(@RequestParam("key") String key);

    @RequestMapping(value = "/expire",method = RequestMethod.GET)
    public  boolean expire(@RequestParam("key") String  key,@RequestParam("seconds")  int seconds);
}
