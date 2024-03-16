package com.jiang.service.impl;


import com.jiang.common.Result;
import com.jiang.service.SearchService;
import com.jiang.service.UserService;
import com.jiang.service.VideoService;
import com.jiang.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 搜索模块的实现类
 *
 * @author SmilngSea
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    private RestHighLevelClient client;


    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 通过es搜索视频
     *
     * @param token 用户的token
     * @param key   搜索的关键关键词
     * @return 通过es返回搜索结果
     */
    @Override
    public Result<HashMap<String, Object>> searchVideo(String token, String key) {
        try {
            // 1.准备Request
            SearchRequest request = new SearchRequest("video");
            // 2.准备DSL
            request.source()
                    .query(QueryBuilders.matchQuery("all", key));
            // 3.发送请求
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            // 4.解析响应
            SearchHits searchHits = response.getHits();
            SearchHit[] hits = searchHits.getHits();
            List<String> json = new ArrayList<>();
            for (SearchHit hit : hits) {
                // 提取文档source
                json.add(hit.getSourceAsString());
            }
            HashMap<String, Object> data = new HashMap<>();
            data.put("video", json);

            //将搜索记录保存到redis
            String redisKey;
            if ("".equals(token)) {
                redisKey = "111";
            } else {
                redisKey = JWTUtils.getIdByToken(token) + "";
                ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
                zSetOperations.add(redisKey, key, System.currentTimeMillis());
            }
            return Result.success(data, "查找成功");
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }


    /**
     * 通过es搜索用户
     *
     * @param token 用户的token
     * @param key   搜索的关键词
     * @return 通过es返回结果
     */
    @Override
    public Result<HashMap<String, Object>> searchUser(String token, String key) {
        try {
            // 1.准备Request
            SearchRequest request = new SearchRequest("user");
            // 2.准备DSL
            request.source()
                    .query(QueryBuilders.matchQuery("all", key));
            // 3.发送请求
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            // 4.解析响应
            SearchHits searchHits = response.getHits();
            SearchHit[] hits = searchHits.getHits();
            List<String> json = new ArrayList<>();
            for (SearchHit hit : hits) {
                // 提取文档source
                json.add(hit.getSourceAsString());
            }
            HashMap<String, Object> data = new HashMap<>();
            data.put("user", json);


            //将搜索记录保存到redis
            String redisKey;
            if ("".equals(token)) {
                redisKey = "111";
            } else {
                redisKey = JWTUtils.getIdByToken(token) + "";
                ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
                zSetOperations.add(redisKey, key, System.currentTimeMillis());
            }
            return Result.success(data, "查找成功！");
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * 获取该用户的搜索列表
     *
     * @param token 用户的token
     * @return 返回用户的搜索列表（关键词）
     */
    @Override
    public Result<HashMap<String, Object>> getSearchList(String token) {
        List<String> list = new ArrayList<>();
        String redisKey;
        if ("".equals(token)) {
            redisKey = "111";
        }
        redisKey = JWTUtils.getIdByToken(token) + "";
        // 获取Redis的操作对象
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();

        // 设置redis的查找范围
        Set<ZSetOperations.TypedTuple<String>> range = zSetOperations.reverseRangeWithScores(redisKey, 0, 5 - 1);
        // 结果的非空判断
        if (range != null) {
            for (ZSetOperations.TypedTuple<String> tuple : range) {
                list.add(tuple.getValue());
            }
            HashMap<String, Object> data = new HashMap<>();
            data.put("list", list);
            return Result.success(data, "查询成功！");
        }
        return Result.error("无搜索记录");
    }
}
