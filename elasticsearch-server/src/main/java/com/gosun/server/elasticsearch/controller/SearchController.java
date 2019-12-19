package com.gosun.server.elasticsearch.controller;

import com.gosun.server.elasticsearch.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 15:15
 */
@RestController
@Api(description = "搜索服务(主要使用es)")
public class SearchController {

    @Resource
    private SearchService searchService;

    /**
     * 搜索日志  --  运营平台的搜索模块
     * @param params
     * @return
     * @throws Exception
     */
    @GetMapping("/searchLog")
    @ApiOperation(value = "条件查询操作日志")
    public List<Map>  search(@ApiParam(name = "params",value = "json格式的条件") @RequestParam("params") String params) throws  Exception{

        return  searchService.searchLog(params);
    }

    /**
     *
     * @param params
     * @return
     * @throws Exception
     */
    @GetMapping("/searchLogCount")
    @PostMapping
    @ApiOperation(value = "条件查询操作日志的记录数")
    public long  searchLogCount(@ApiParam(name = "params",value = "json格式的条件") @RequestParam("params") String params) throws  Exception{

        return  searchService.searchLogCount(params);
    }

    /**
     * 求api的执行的平均时间  监控系统
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    @GetMapping("/statAvg")
    @ApiOperation(value = "条件查询api调用平均时长")
    public Map  statAvg(@ApiParam(name = "startTime",value = "开始时间") @RequestParam("startTime")long startTime,@ApiParam(name = "endTime",value = "结束时间")@RequestParam("endTime") long endTime) throws  Exception{

        return  searchService.statAvg(startTime,endTime);
    }



}
