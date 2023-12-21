package com.jiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.common.Result;
import com.jiang.domain.VideoDO;
import com.jiang.mapper.VideoMapper;
import com.jiang.service.VideoService;
import com.jiang.util.COSUtils;
import com.jiang.util.JWTUtils;
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

@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, VideoDO> implements VideoService {
    @Autowired
    private VideoService videoService;


    @Autowired
    private RedisTemplate redisTemplate;

    public boolean isRedisTokenExist(String token) {
        return redisTemplate.hasKey(JWTUtils.getIdByToken(token));
    }


    /**
     * 上传视频
     * @param token
     * @param multipartFile
     * @param title
     * @param type
     * @return
     * @throws IOException
     */
    @Override
    public Result<HashMap<String, Object>> save(String token, MultipartFile multipartFile, String title, String type) throws IOException {
        if (token.isEmpty()) {
            return Result.error("未接收到token");
        } else if (isRedisTokenExist(token)) {
            Long usrid = JWTUtils.getIdByToken(token);

        File file = File.createTempFile(String.valueOf(IdWorker.getId()), "mp4");
        multipartFile.transferTo(file);


        String url = COSUtils.UploadVideo(file);


        VideoDO videoDO = new VideoDO();
        videoDO.setUrl(url);
        videoDO.setType(type);
        videoDO.setTitle(title);
        videoDO.setTime(new Date());
        videoDO.setUsrid(usrid);

        videoService.save(videoDO);

        HashMap<String, Object> data = new HashMap<>();
        data.put("id",videoDO.getId());

        return Result.success(data,"上传视频成功");
    }else {
            return Result.error("未登录");
        }
    }


    /**
     * 点击视频
     * @param videoDO
     * @return
     */
    @Override
    public Result<HashMap<String, Object>> click(VideoDO videoDO) {
        HashMap<String, Object> data = new HashMap<>();
        Long id = videoDO.getId();
        if(!redisTemplate.boundZSetOps("click").getOperations().hasKey(id)) {
            //在mysql中根据视频id查找视频的url
            LambdaQueryWrapper<VideoDO> lambdaQueryWrapper = new LambdaQueryWrapper<VideoDO>();
            lambdaQueryWrapper.eq(VideoDO::getId, id);
            VideoDO video = videoService.getOne(lambdaQueryWrapper);
            String url = video.getUrl();
            // redis事务
            SessionCallback sessionCallback = new SessionCallback() {
                @Override
                public Object execute(RedisOperations redisOperations) throws DataAccessException {
                    redisOperations.multi();
                    redisTemplate.boundZSetOps("click").incrementScore(id,1);
                    redisTemplate.opsForValue().set(id,url);
                    return redisOperations.exec();
                }
            };
            redisTemplate.execute(sessionCallback);

            data.put("id",id);
            data.put("url",url);
            return Result.success(data,"点击量+1");
        }
        redisTemplate.boundZSetOps("click").incrementScore(id,1);
        String url = (String) redisTemplate.opsForValue().get(id);
        data.put("id",id);
        data.put("url",url);
        return Result.success(data,"点击量+1");
    }

    /**
     * 获取点击量排行榜
     * @return
     */
    @Override
    public Result<HashMap<String, Object>> getRank() {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        HashMap<String, Object> data = new HashMap<>();
        Set click = zSetOperations.rangeByScore("click", 1, Integer.MAX_VALUE,0,30);
        for (Object id : click) {
            String url = (String)redisTemplate.opsForValue().get(id);
            data.put(id.toString(),url);

        }
        return Result.success(data,"查询成功");
    }
}
