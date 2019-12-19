package com.gosun.server.cache.controller;

import com.gosun.server.cache.service.LockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 12:02
 */
@RestController
@Api(value = "分布式锁在线api",description = "分布式锁在线api")
public class LockController {
    @Resource
    private LockService lockService;

//    @RequestMapping(value = "/getLock",method = RequestMethod.GET)
//    @ApiOperation("得到分布式锁")
//    public RLock set(@ApiParam(name = "lock",value = "锁的名称",defaultValue = "lock") @RequestParam("lock") String lock){
//
//        return lockService.getLock(lock);
//
//    }

    @RequestMapping(value = "/lock",method = RequestMethod.GET)
    @ApiOperation("得到分布式锁")
    public void lock(@ApiParam(name = "lock",value = "锁的名称",defaultValue = "lock") @RequestParam("lock") String lock){

        lockService.getLock(lock).lock();

    }

    @RequestMapping(value = "/unlock",method = RequestMethod.GET)
    @ApiOperation("释放分布式锁")
    public void unlock(@ApiParam(name = "lock",value = "锁的名称",defaultValue = "lock") @RequestParam("lock") String lock){

        lockService.getLock(lock).unlock();

    }
}
