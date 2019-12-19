package com.gosun.server.cache.service.impl;

import com.gosun.server.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 11:57
 */
@Service
public class CacheServiceImpl implements CacheService {
    //ssm+redis

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public void set(String key, String value) {

        stringRedisTemplate.opsForValue().set(key,value);

    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public long incr(String key) {
        //
        return stringRedisTemplate.opsForValue().increment(key,1);
    }

    @Override
    public long decr(String key) {
        return stringRedisTemplate.opsForValue().increment(key,-1);
    }

    @Override
    public long incrBy(String key, long num) {
        return stringRedisTemplate.opsForValue().increment(key,num);
    }

    @Override
    public long decrBy(String key, long num) {
        return stringRedisTemplate.opsForValue().increment(key,-num);
    }

    @Override
    public boolean del(String key) {
        return stringRedisTemplate.delete(key);
    }

    @Override
    public boolean existsKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    @Override
    public boolean expire(String key, long seconds) {
        return stringRedisTemplate.expire(key,seconds, TimeUnit.SECONDS);
    }

    @Override
    public long lpush(String key, String[] values) {
        return stringRedisTemplate.opsForList().leftPushAll(key,values);
    }

    @Override
    public String lpop(String key) {
        return stringRedisTemplate.opsForList().leftPop(key);
    }
    public String  rpop(String key){
        return stringRedisTemplate.opsForList().rightPop(key);

    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        return stringRedisTemplate.opsForList().range(key,start,end);
    }

    @Override
    public boolean hset(String key, String field, String value) {
        try {
            stringRedisTemplate.opsForHash().put(key, field, value);
            return  true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  false;
    }

    @Override
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            stringRedisTemplate.opsForHash().putAll(key, map);
            return  true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String hget(String key, String field) {

        Object o =  stringRedisTemplate.opsForHash().get(key,field);

        return o+"";
    }

    @Override
    public Map<String, Object> hGetAll(String key) {
        Map<String, Object> map = new HashMap<>();

        Set fields = stringRedisTemplate.opsForHash().keys(key);
        for (Object field : fields) {
            Object value = stringRedisTemplate.opsForHash().get(key,field);
            map.put(field+"",value);
        }

        return map;
    }

    @Override
    public long sadd(String key, String[] members) {
        return  stringRedisTemplate.opsForSet().add(key,members);
    }

    @Override
    public long srem(String key, String member) {
        return stringRedisTemplate.opsForSet().remove(key,member);
    }

    @Override
    public String spop(String key) {
        return stringRedisTemplate.opsForSet().pop(key);
    }

    @Override
    public Set<String> smembers(String key) {
        return stringRedisTemplate.opsForSet().members(key);
    }

    @Override
    public boolean zadd(String key, String value,double score) {
        return stringRedisTemplate.opsForZSet().add(key,value,score);
    }

    @Override
    public  Map<String, Double> sRangeWithScore(String key, long start,long end) {
        Map<String, Double> map = new HashMap<>();
        Set<ZSetOperations.TypedTuple<String>> set =   stringRedisTemplate.opsForZSet().rangeWithScores(key,start,end);
        for (ZSetOperations.TypedTuple<String> stringTypedTuple : set) {
            String  value = stringTypedTuple.getValue();
            Double score = stringTypedTuple.getScore();
            map.put(value,score);

        }

        return  map;
    }
}
