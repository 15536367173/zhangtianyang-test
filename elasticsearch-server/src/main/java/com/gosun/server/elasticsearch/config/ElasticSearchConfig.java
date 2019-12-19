package com.gosun.server.elasticsearch.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: qwerty
 * @User: DELL
 * @Company: 高创安邦
 * @Author: 张天阳
 * @Date: 2019/12/17
 * @Time: 10:41
 */
@Configuration
public class ElasticSearchConfig {

    @Value("${spring.data.elasticsearch.host}")
    private  String host;//xxx,xxx,xxx
    @Value("${spring.data.elasticsearch.port}")
    private  int port;
    @Value("${spring.data.elasticsearch.connectTimeOut}")
    private int connectTimeOut = 1000; // 连接超时时间
    @Value("${spring.data.elasticsearch.socketTimeOut}")
    private int socketTimeOut = 30000; // 连接超时时间
    @Value("${spring.data.elasticsearch.connectionRequestTimeOut}")
    private int connectionRequestTimeOut = 500; // 获取连接的超时时间
    @Value("${spring.data.elasticsearch.maxConnectNum}")
    private int maxConnectNum = 100; // 最大连接数
    @Value("${spring.data.elasticsearch.maxConnectPerRoute}")
    private int maxConnectPerRoute = 100; // 最大路由连接数
    private RestClientBuilder builder;
    private List<HttpHost> httpHostList;
    private static final String SCHEMA="http";

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        httpHostList = new ArrayList<>();
        String hosts [] = host.split(",");
        for (String s : hosts) {
            //String hostname, int port, String scheme
            HttpHost httpHost = new HttpHost(s,port,SCHEMA);
            httpHostList.add(httpHost);
        }

        //list--->array
        //httpHostList.toArray();

        builder = RestClient.builder(httpHostList.toArray(new HttpHost[]{}));

        setConnectTimeOutConfig();
        setMutiConnectConfig();

        RestHighLevelClient highLevelClient = new RestHighLevelClient(builder);

        return  highLevelClient;
    }

    // 异步httpclient的连接延时配置
    public void setConnectTimeOutConfig() {
        builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                requestConfigBuilder.setConnectTimeout(connectTimeOut);
                requestConfigBuilder.setSocketTimeout(socketTimeOut);
                requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
                return requestConfigBuilder;
            }
        });
    }

    // 异步httpclient的连接数配置
    public void setMutiConnectConfig() {
        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                httpClientBuilder.setMaxConnTotal(maxConnectNum);
                httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
                return httpClientBuilder;
            }
        });
    }
}
