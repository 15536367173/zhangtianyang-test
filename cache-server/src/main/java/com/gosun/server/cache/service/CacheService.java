package com.gosun.server.cache.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 11:56
 */
public interface CacheService {
    //string
    public  void  set(String key,String value);
    public  String  get(String key);
    public  long incr(String key);
    public  long decr(String key);
    public  long incrBy(String key,long num);
    public  long decrBy(String key,long num);

    public  boolean del(String key);
    public  boolean existsKey(String key);
    public  boolean expire(String key,long seconds);

    //list
    public long  lpush(String key,String...values);
    public String  lpop(String key);
    public String  rpop(String key);
    public List<String> lrange(String key, long start, long end);

    //hash
    public  boolean hset(String key,String field,String value);
    public  boolean hmset(String key, Map<String,Object> map);
    public  String hget(String key,String field);
    public Map<String,Object> hGetAll(String key);

    //set
    public  long sadd(String key,String ... members);
    public  long srem(String key,String member);

    /**
     *抽奖
     */
    public   String spop(String key);

    public Set<String> smembers(String key);

    //zset
    public boolean zadd(String key, String value,double score) ;


    public Map<String, Double> sRangeWithScore(String key,long start,long end);
}
