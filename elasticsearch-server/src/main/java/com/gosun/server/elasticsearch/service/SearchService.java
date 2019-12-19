package com.gosun.server.elasticsearch.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 10:54
 */
public interface SearchService {
    public void createIndex() throws IOException;

    public boolean existsIndex() throws IOException;

    public void  delIndex() throws IOException;


    public  void  addLog(String  json)throws IOException;

    public  void  queryAll()throws IOException;


    /**
     *  运营人员调用
     * @param params 条件  {"appKey","haier","requestContent":"海尔"}
     * @return  满足条件的结果
     */
    public List<Map>  searchLog(String params) throws IOException;

    public long searchLogCount(String params)throws IOException;


    // 查询每个api的平均执行时间 （聚合查询）
    // ------> 监控系统，监控api的调用时间，时间超过阈值，发送警告

    public Map<String,Long>  statAvg(long startTime,long endTime) throws  IOException;
}
