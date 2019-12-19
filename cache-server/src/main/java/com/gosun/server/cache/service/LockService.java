package com.gosun.server.cache.service;

import org.redisson.api.RLock;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 11:56
 */
public interface LockService {
    //得到一个分布式锁
    public RLock getLock(String lock);
}
