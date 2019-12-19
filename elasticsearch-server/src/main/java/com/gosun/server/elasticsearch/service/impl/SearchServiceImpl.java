package com.gosun.server.elasticsearch.service.impl;

import com.gosun.server.elasticsearch.service.SearchService;
import com.alibaba.fastjson.JSONObject;
import com.gosun.server.elasticsearch.utils.SearchUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 10:55
 */

@Service
public class SearchServiceImpl implements SearchService {
    //操作es，RestHighLevelClient

    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * elasticsearch.index.name=gateway_log_test
     * elasticsearch.index.type=gateway_log_test_type
     */
    @Value("${elasticsearch.index.name}")
    private  String  indexName;
    @Value("${elasticsearch.index.type}")
    private  String  indexType;

    private Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);



    @Override
    public void createIndex() throws IOException {
        if (!existsIndex()){//不存在
            //创建索引
            CreateIndexRequest indexRequest = new CreateIndexRequest(indexName);

            //指定索引的分片规则
            SearchUtils.buildSetting(indexRequest);
            SearchUtils.buildIndexMapping(indexRequest,indexType);

            CreateIndexResponse response = restHighLevelClient.indices().create(indexRequest,RequestOptions.DEFAULT);

            boolean status =  response.isAcknowledged();
            logger.info("索引"+indexName+"创建结果是："+status);
            //System.out.println(status);
        }else{
            logger.debug("索引"+indexName+"已经存在");

        }

    }

    @Override
    public boolean existsIndex() throws IOException {
        //1，创建GetRequest对象 //2,指定索引
        GetIndexRequest getRequest = new GetIndexRequest();
        getRequest.indices(indexName);

        //GetRequest getRequest, RequestOptions options
        return  restHighLevelClient.indices().exists(getRequest,RequestOptions.DEFAULT);

    }

    @Override
    public void delIndex() throws IOException {
        DeleteIndexRequest deleteRequest = new DeleteIndexRequest(indexName);

        AcknowledgedResponse response =   restHighLevelClient.indices().delete(deleteRequest,RequestOptions.DEFAULT);

        logger.debug("索引"+indexName+"删除的结果是："+response.isAcknowledged());

    }

    @Override
    public void addLog(String json) throws IOException {
        //没有业务id，es会分配随机id
        IndexRequest indexRequest = new IndexRequest(indexName,indexType);
        //有业务id，es可以使用业务id作为该数据的id
        // IndexRequest indexRequest = new IndexRequest(indexName,indexType,"1");
        indexRequest.source(json,XContentType.JSON);

        IndexResponse response = restHighLevelClient.index(indexRequest,RequestOptions.DEFAULT);

        logger.info("新增数据结果："+response.status().getStatus());
    }

    @Override
    public void queryAll() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.types(indexName);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

        SearchHits searchHits =  searchResponse.getHits();
        System.out.println("总的记录数："+searchHits.getTotalHits());
        SearchHit searchHit [] = searchHits.getHits();
        for (SearchHit hit : searchHit) {
            System.out.println(hit.getSourceAsMap());
        }


    }

    /**
     * 搜索日志
     * @param params 条件  {"appKey","haier","requestContent":"海尔"}
     * @return
     * @throws IOException
     */
    @Override
    public List<Map> searchLog(String params) throws IOException {
        //1,创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(indexType);

        SearchSourceBuilder searchSourceBuilder = SearchUtils.builderSearchParams(params);

        JSONObject jsonObject = JSONObject.parseObject(params);
        //分页部分
        int startIndex = jsonObject.getInteger("startIndex");
        int rows = jsonObject.getInteger("rows");
        searchSourceBuilder.from(startIndex);
        searchSourceBuilder.size(rows);

        //高亮显示部分
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("requestContent")
                .preTags("<em style=\"color:red;\">")
                .postTags("</em>");
        searchSourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(searchSourceBuilder);

        SearchResponse response =   restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

        //处理结果
        SearchHits searchHits =    response.getHits();

        long total = searchHits.getTotalHits();
        logger.info("满足条件的记录数："+total);

        SearchHit searchHit [] =   searchHits.getHits();
        List<Map> list = new ArrayList<>();
        for (SearchHit hit : searchHit) {
            //{receiveTime=1573701950471, apiName=jingdong.goods.find, remoteIp=192.128.82.9, createTime=1573701950471, venderId=1, platformRepTime=600, errorCode=0, appKey=haier, requestContent=海尔冰箱, servIP=192.168.82.9}
            Map<String, Object> map =   hit.getSourceAsMap();
            //System.out.println(map);

            //高亮部分的内容
            Map<String, HighlightField> highlightFieldMap =  hit.getHighlightFields();

            //System.out.println(highlightFieldMap.get("requestContent"));
            //[requestContent], fragments[[<em style="color:red;">海尔</em>冰箱]]
            //取出高亮的内容
            HighlightField highlightField =  highlightFieldMap.get("requestContent");
            if (highlightField!=null){
                Text[] texts =  highlightField.getFragments();
                if (texts!=null){
                    String value = texts[0].toString();
                    //System.out.println("高亮"+value);
                    //高亮显示的内容-->替代非高亮内容
                    map.put("requestContent",value);

                }
            }

            list.add(map);
        }
        logger.info("result data:"+list);
        return list;
    }

    @Override
    public long searchLogCount(String params) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(indexType);

        //条件
        SearchSourceBuilder searchSourceBuilder = SearchUtils.builderSearchParams(params);
        //指定条件
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse =   restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

        SearchHits searchHits = searchResponse.getHits();

        return searchHits.getTotalHits();
    }

    /**
     * 查询每个api的平均执行时间 （聚合查询）
     * 思路：
     *  按照请求时间的范围条件查询(receiveTime)
     *  按照apiName分组
     *  分组后聚合查询平均执行时间（platformRepTime）
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Map<String, Long> statAvg(long startTime, long endTime) throws  IOException {
        //1，创建查询请求对象
        SearchRequest searchRequest = new SearchRequest(indexName);
        //2,指定查询类型
        searchRequest.types(indexType);
        //3,查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.rangeQuery("receiveTime").gte(startTime).lte(endTime));
        //4,分组
        //分组字段
        AggregationBuilder aggregationBuilder =
                AggregationBuilders.terms("apiName_group").field("apiName");
        //分组后聚合查询（求平均值）
        aggregationBuilder.subAggregation(AggregationBuilders.avg("avg_platformRepTime").field("platformRepTime"));
        searchSourceBuilder.aggregation(aggregationBuilder);
        //指定条件 和 聚合查询的条件等
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

        /**
         * "aggregations": {
         *     "sex_name": {
         *       "doc_count_error_upper_bound": 0,
         *       "sum_other_doc_count": 0,
         *       "buckets": [
         *         {
         *           "key": "女",
         *           "doc_count": 5,
         *           "age_avg": {
         *             "value": 30
         *           }
         *         },
         */
        //聚合内容
        Aggregations aggregations = searchResponse.getAggregations();
        Terms terms =  aggregations.get("apiName_group");
        List<? extends Terms.Bucket> list = terms.getBuckets();
        Map<String,Long> longMap = new HashMap<>();
        for (Terms.Bucket bucket : list) {
            //得到每个组
            Object  key = bucket.getKey();
            //jingdong.goods.find(男)  980ms     23
            //jingdong.order.cancel(女)  3020ms  18

            //该组（某个api）的平均执行时间
            Aggregations avgAggregation =  bucket.getAggregations();
            Avg avg =  avgAggregation.get("avg_platformRepTime");
            Double  avgValue = avg.getValue();
            long avgTime = avgValue.longValue();

            longMap.put(key+"",avgTime);
        }


        return longMap;
    }
}
