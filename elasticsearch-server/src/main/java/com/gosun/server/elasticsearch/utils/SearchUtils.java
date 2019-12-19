package com.gosun.server.elasticsearch.utils;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 10:52
 */
public class SearchUtils {
    public static void buildIndexMapping(CreateIndexRequest request, String type) throws IOException {
        XContentBuilder mappingBuilder = JsonXContent.contentBuilder()
                .startObject()
                .startObject("properties")
                .startObject("appkey")
                .field("type", "keyword")  //能聚合查询
                .field("index", "true")
                .endObject()

                .startObject("servIP")
                .field("type", "ip")
                .field("index", "true")
                .endObject()

                .startObject("venderId")
                .field("type", "long")
                .field("index", "true")
                .endObject()

                .startObject("remoteIp")
                .field("type", "ip")
                .field("index", "true")
                .endObject()

                .startObject("apiName")
                .field("type", "keyword")
                .field("index", "true")
                .endObject()

//                .startObject("totalRepTime")
//                .field("type", "long")
//                .field("index", "true")
//                .endObject()

                .startObject("platformRepTime")
                .field("type", "long")
                .field("index", "true")
                .endObject()

                .startObject("requestContent")
                .field("type", "text")//不能聚合，能分词
                .field("analyzer", "ik_max_word")
                .field("index", "true")
                .endObject()

                .startObject("errorCode")
                .field("type", "keyword")
                .field("index", "true")
                .endObject()

                .startObject("receiveTime")
                .field("type", "date")
                .field("index", "true")
//                .field("format", "yyyy-MM-dd HH:mm:ss")
                .endObject()

                .startObject("createTime")
                .field("type", "date")
                .field("index", "true")
                .endObject()
                .endObject()
                .endObject();
        request.mapping(type, mappingBuilder);
    }

    //设置分片
    public  static void buildSetting(CreateIndexRequest request) {
        request.settings(Settings.builder().put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2));
    }


    public  static SearchSourceBuilder builderSearchParams(String params){

        //2,指定搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //params 取出条件
        JSONObject jsonObject = JSONObject.parseObject(params);
        String appKey = jsonObject.getString("appKey");
        String apiName = jsonObject.getString("apiName");
        String requestContent = jsonObject.getString("requestContent");
        Long start = jsonObject.getLong("start");
        Long end = jsonObject.getLong("end");
        //keyword ---> term
        TermQueryBuilder appKeyTermQuery = null;
        TermQueryBuilder  apiNameTermQuery = null;
        RangeQueryBuilder rangeQueryBuilder = null;
        MatchQueryBuilder matchQueryBuilder = null;
        StringBuilder sb = new StringBuilder("条件：");

        if (appKey!=null&&!appKey.equals("")){//   appKey:""
            appKeyTermQuery = QueryBuilders.termQuery("appKey",appKey);
            sb.append("appKey="+appKey);
        }
        if (apiName!=null&&!apiName.equals("")){
            apiNameTermQuery = QueryBuilders.termQuery("apiName",apiName);
            sb.append(",apiName="+apiName);
        }

        if (start!=null&&end!=null){
            rangeQueryBuilder = QueryBuilders.rangeQuery("receiveTime").gte(start).lte(end);
            sb.append("," +start+"<=receiveTime<="+end);

        }
        if (requestContent!=null&&!requestContent.equals("")){
            matchQueryBuilder = QueryBuilders.matchQuery("requestContent",requestContent);
            sb.append(",requestContent like "+requestContent);
        }

        //logger.info(sb.toString());

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if (appKeyTermQuery!=null){
            boolQueryBuilder.must(appKeyTermQuery);
        }
        if(apiNameTermQuery!=null){
            boolQueryBuilder.must(apiNameTermQuery);
        }
        if(rangeQueryBuilder!=null){
            boolQueryBuilder.must(rangeQueryBuilder);
        }
        if (matchQueryBuilder!=null){
            boolQueryBuilder.must(matchQueryBuilder);
        }
        //查询条件部分
        searchSourceBuilder.query(boolQueryBuilder);

        return  searchSourceBuilder;
    }
}
