package com.gosun.server.gateway.feign;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 11:41
 */
@Component
public class CacheServiceFallback implements CacheService {
    @Override
    public void set(String key, String value) {

    }

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public boolean hset(String key, String field, String value) {
        return false;
    }

    @Override
    public boolean hmset(String key, Map<String, Object> map) {
        return false;
    }

    @Override
    public String hget(String key, String field) {
        return null;
    }

    @Override
    public Map<String, Object> hGetAll(String key) {
        return null;
    }

    @Override
    public boolean del(String key) {
        return false;
    }

    @Override
    public long incr(String key) {
        return 0;
    }

    @Override
    public boolean expire(String key, int seconds) {
        return false;
    }
}
