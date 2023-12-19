package com.jiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jiang.common.Result;
import com.jiang.domain.UserDO;
import com.jiang.domain.VideoDO;
import com.jiang.service.SearchService;
import com.jiang.service.UserService;
import com.jiang.service.VideoService;
import com.jiang.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public Result<HashMap<String, Object>> searchVideo(String token,String key) {

        //构造条件构造器
        LambdaQueryWrapper<VideoDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(VideoDO::getTitle, key)
                .or().like(VideoDO::getTime, key)
                .or().like(VideoDO::getType, key);

        List<VideoDO> list = videoService.list(queryWrapper);

        HashMap<String, Object> data = new HashMap<>();
        data.put("video", list);

        //将搜索记录保存到redis
        String redisKey;
        if(token.equals("")){
            redisKey = "111";
        }
        redisKey = JWTUtils.getIdByToken(token)+"";
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add(redisKey, key, System.currentTimeMillis());

        return Result.success(data, "查找成功");
    }

    @Override
    public Result<HashMap<String, Object>> searchUser(String token,String key) {
        // TODO: 隐藏密码等隐私数据

        //构造条件构造器
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(true,UserDO::getName,key);

        List<UserDO> list = userService.list(queryWrapper);

        HashMap<String, Object> data = new HashMap<>();
        data.put("user",list);

        //将搜索记录保存到redis
        String redisKey;
        if(token.equals("")){
            redisKey = "111";
        }
        redisKey = JWTUtils.getIdByToken(token)+"";
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add(redisKey, key, System.currentTimeMillis());

        return Result.success(data,"查找成功！");
    }

    @Override
    public Result<HashMap<String, Object>> getSearchList(String token) {
        List<String> list = new ArrayList<>();
        String redisKey;
        if (token.equals("")){
            redisKey = "111";
        }
        redisKey = JWTUtils.getIdByToken(token)+"";
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();

        Set<ZSetOperations.TypedTuple<String>> range = zSetOperations.reverseRangeWithScores(redisKey, 0, 5 - 1);
        for (ZSetOperations.TypedTuple<String> tuple : range) {
            list.add(tuple.getValue());
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("list",list);
        return Result.success(data,"查询成功！");
    }
}
