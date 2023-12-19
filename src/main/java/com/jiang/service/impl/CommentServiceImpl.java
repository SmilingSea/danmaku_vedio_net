package com.jiang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.common.Result;
import com.jiang.domain.CommentDO;
import com.jiang.mapper.CommentMapper;
import com.jiang.service.CommentService;
import com.jiang.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, CommentDO> implements CommentService {

    @Autowired
    private CommentService commentService;

    @Autowired
    private RedisTemplate redisTemplate;

    public boolean isRedisTokenExist(String token) {
        return redisTemplate.hasKey(JWTUtils.getIdByToken(token));
    }
    @Override
    public Result<HashMap<String, Object>> save(String token, CommentDO comment) {
        if (token.isEmpty()) {
            return Result.error("未接收到token");
        } else if (isRedisTokenExist(token)) {
        comment.setCreateTime(new Date());
        comment.setUserId(JWTUtils.getIdByToken(token));
        commentService.save(comment);
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", comment.getId());

        return Result.success(data, "评论成功！");
    } else {
            return Result.error("未登录");
        }
    }
}
