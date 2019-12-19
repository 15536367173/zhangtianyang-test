package com.gosun.server.cache.service.impl;


import com.gosun.server.cache.service.LockService;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Service;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 11:59
 */
@Service
public class LockServiceImpl implements LockService {
    @Override
    public RLock getLock(String lock) {
        //1,创建Config
        Config config = new Config();
        //2,设置服务地址和密码
        // config.useClusterServers();  集群
        config.useSingleServer().setAddress("redis://192.168.82.188:6379");
        config.useSingleServer().setPassword("jbgsn");

        //3,创建RedissionClient对象
        RedissonClient redissonClient = Redisson.create(config);

        //4,获得锁

        RLock rLock = redissonClient.getLock(lock);


        return rLock;
    }
}
