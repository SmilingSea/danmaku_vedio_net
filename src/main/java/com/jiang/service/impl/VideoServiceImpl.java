package com.jiang.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.common.Result;
import com.jiang.domain.VideoDO;
import com.jiang.mapper.VideoMapper;
import com.jiang.service.VideoService;
import com.jiang.util.COSUtils;
import com.jiang.util.JWTUtils;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * 视频的实现类
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, VideoDO> implements VideoService {
    @Autowired
    private VideoService videoService;

    @Autowired
    private RestHighLevelClient client;


    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 判断Redis是否存在
     * @param token 用户的token
     * @return 返回是否存在token
     */
    public boolean isRedisTokenExist(String token) {
        return redisTemplate.hasKey(JWTUtils.getIdByToken(token));
    }


    /**
     * 上传视频
     *
     * @param token 用户token
     * @param multipartFile 上传的视频文件
     * @param title 视频标题
     * @param type 视频类型
     * @return 返回Result结果
     * @throws IOException 抛出异常
     */
    @Override
    public Result<HashMap<String, Object>> save(String token, MultipartFile multipartFile, String title, String type){
        try {
            if (token.isEmpty()) {
                return Result.error("未接收到token");
            } else if (isRedisTokenExist(token)) {
                Long usrid = JWTUtils.getIdByToken(token);

                // 将MultipartFile转换成File类型
                File file = File.createTempFile(String.valueOf(IdWorker.getId()), "mp4");
                multipartFile.transferTo(file);

                // 将视频上传到腾讯云
                String url = COSUtils.UploadVideo(file);

                // 将数据保存到数据库
                VideoDO videoDO = new VideoDO();
                videoDO.setUrl(url);
                videoDO.setType(type);
                videoDO.setTitle(title);
                videoDO.setTime(new Date());
                videoDO.setUsrid(usrid);

                videoService.save(videoDO);

                HashMap<String, Object> data = new HashMap<>();
                data.put("id", videoDO.getId());

                // 将视频信息同步到es
                String json = JSON.toJSONString(videoDO);

                // 1.准备request对象
                IndexRequest request = new IndexRequest("video").id(videoDO.getId().toString());
                // 2.准备json文档
                request.source(json, XContentType.JSON);
                // 3.发送请求
                client.index(request, RequestOptions.DEFAULT);

                return Result.success(data, "上传视频成功");
            } else {
                return Result.error("未登录");
            }
        }catch (Exception e){
            throw new RuntimeException();
        }

    }


    /**
     * 点击视频
     *
     * @param videoDO 视频对象
     * @return 返回Reuslt结果
     */
    @Override
    public Result<HashMap<String, Object>> click(VideoDO videoDO) {
        HashMap<String, Object> data = new HashMap<>();
        Long id = videoDO.getId();
        // 判断redis中是否已经存在该视频的点击量数据
        if (!redisTemplate.boundZSetOps("click").getOperations().hasKey(id)) {
            //在mysql中根据视频id查找视频的url
            LambdaQueryWrapper<VideoDO> lambdaQueryWrapper = new LambdaQueryWrapper<VideoDO>();
            lambdaQueryWrapper.eq(VideoDO::getId, id);
            VideoDO video = videoService.getOne(lambdaQueryWrapper);
            String url = video.getUrl();
            // 创建redis事务对象并定义方法
            SessionCallback sessionCallback = new SessionCallback() {
                @Override
                public Object execute(RedisOperations redisOperations) throws DataAccessException {
                    // 开启事务
                    redisOperations.multi();
                    // 队列任务
                    redisTemplate.boundZSetOps("click").incrementScore(id, 1);
                    redisTemplate.opsForValue().set(id, url);
                    // 执行任务队列中的所有命令并结束事务
                    return redisOperations.exec();
                }
            };
            // 执行事务
            redisTemplate.execute(sessionCallback);

            data.put("id", id);
            data.put("url", url);
            return Result.success(data, "点击量+1");
        }
        // redis中已经存在点击量信息，将点击量数据+1
        redisTemplate.boundZSetOps("click").incrementScore(id, 1);
        String url = (String) redisTemplate.opsForValue().get(id);
        data.put("id", id);
        data.put("url", url);
        return Result.success(data, "点击量+1");
    }

    /**
     * 获取点击量排行榜
     *
     * @return 返回点击量排行榜
     */
    @Override
    public Result<HashMap<String, Object>> getRank() {
        // 获取redis操作对象
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        HashMap<String, Object> data = new HashMap<>();
        // 通过redis获取点击量排行榜前30名
        Set click = zSetOperations.rangeByScore("click", 1, Integer.MAX_VALUE, 0, 30);
        for (Object id : click) {
            String url = (String) redisTemplate.opsForValue().get(id);
            data.put(id.toString(), url);
        }
        return Result.success(data, "查询成功");
    }
}
