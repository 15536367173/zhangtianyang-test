package com.gosun.server.cache.controller;

import com.gosun.server.cache.service.CacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 12:00
 */
@RestController
@Api(value = "缓存在线api",description = "缓存在线api")
public class CacheController  {
    @Resource
    private CacheService cacheService;


    @RequestMapping(value = "/set",method = RequestMethod.GET)
    @ApiOperation("设置String类型的键值对")
    public  void set(@ApiParam(name = "key",value = "键",defaultValue = "name") @RequestParam("key") String  key, @ApiParam(name = "value",value = "值",defaultValue = "admin")@RequestParam("value")  String value){
        cacheService.set(key,value);

    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ApiOperation("根据key获取String类型的值")
    public  String get(@ApiParam(name = "key",value = "键",defaultValue = "name") @RequestParam("key") String  key){
        return cacheService.get(key);
    }


    @RequestMapping(value = "/hset",method = RequestMethod.GET)
    @ApiOperation("存储一个键值对到hash结构中")
    public  boolean hset(
            @ApiParam(name = "key",value = "键",defaultValue = "user")@RequestParam("key") String key,
            @ApiParam(name = "field",value = "字段",defaultValue = "name")@RequestParam("field")String field,
            @ApiParam(name = "value",value = "值",defaultValue = "jack")@RequestParam("value")String value){
        return  cacheService.hset(key,field,value);

    }
    @RequestMapping(value = "/hmset",method = RequestMethod.POST)
    @ApiOperation("存储多个键值对到hash结构中")
    public  boolean hmset(@ApiParam(name = "key",value = "键",defaultValue = "user") @RequestParam("key") String key,
                          @ApiParam(name = "map",value = "map类型的值")@RequestBody Map<String,Object> map){
        return  cacheService.hmset(key,map);
    }

    @RequestMapping(value = "/hget",method = RequestMethod.GET)
    @ApiOperation("根据key和field得到hash结构中的值")
    public  String hget(@ApiParam(name = "key",value = "键",defaultValue = "user")@RequestParam("key") String key,
                        @ApiParam(name = "field",value = "字段",defaultValue = "username")@RequestParam("field") String field){

        return  cacheService.hget(key,field);
    }
    @RequestMapping(value = "/hgetAll",method = RequestMethod.GET)
    @ApiOperation("得到hash结构中所有键值对")
    public  Map<String,Object> hGetAll(@ApiParam(name = "key",value = "键",defaultValue = "user")@RequestParam("key") String key){

        return  cacheService.hGetAll(key);
    }

    @RequestMapping(value = "/del",method = RequestMethod.GET)
    @ApiOperation("根据key删除缓存中的数据")
    public boolean  del(@ApiParam(name = "key",value = "键",defaultValue = "user")@RequestParam("key") String key){

        return  cacheService.del(key);
    }

    @RequestMapping(value = "/incr",method = RequestMethod.GET)
    @ApiOperation("自增操作")
    public long  incr(@ApiParam(name = "key",value = "键",defaultValue = "user")@RequestParam("key") String key){

        return  cacheService.incr(key);
    }

    @RequestMapping(value = "/expire",method = RequestMethod.GET)
    @ApiOperation("设置key的过期时间")
    public  boolean expire(@ApiParam(name = "key",value = "键",defaultValue = "name") @RequestParam("key") String  key,@ApiParam(name = "seconds",value = "时间",defaultValue = "5")@RequestParam("seconds")  int seconds) {
        return cacheService.expire(key, seconds);
    }
}
