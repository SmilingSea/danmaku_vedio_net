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

/**
 * 评论模块的实现类
 * @author SmilingSea
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, CommentDO> implements CommentService {

    @Autowired
    private CommentService commentService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 判断token是否存在
     * @param token 用户的token
     * @return 返回是否存在token
     */
    public boolean isRedisTokenExist(String token) {
        return redisTemplate.hasKey(JWTUtils.getIdByToken(token));
    }

    /**
     * 发表评论
     *
     * @param token   用户的token
     * @param comment 评论的内容
     * @return 返回Result结果集
     */
    @Override
    public Result<HashMap<String, Object>> save(String token, CommentDO comment) {
        // 判断token非空
        if (token.isEmpty()) {
            return Result.error("未接收到token");
        } else if (isRedisTokenExist(token)) {

            // 使用mybatisPlus添加数据
            comment.setCreateTime(new Date());
            comment.setUserId(JWTUtils.getIdByToken(token));
            commentService.save(comment);
            HashMap<String, Object> data = new HashMap<>();
            data.put("id", comment.getId());

            // 返回结果
            return Result.success(data, "评论成功！");
        } else {
            return Result.error("未登录");
        }
    }
}
