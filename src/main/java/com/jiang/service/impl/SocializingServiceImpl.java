package com.jiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.common.Result;
import com.jiang.domain.SocializingDO;
import com.jiang.domain.UserDO;
import com.jiang.mapper.SocializingMapper;
import com.jiang.service.SocializingService;
import com.jiang.service.UserService;
import com.jiang.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 社交模块的实现类
 *
 * @author SmilngSea
 */
@Service
public class SocializingServiceImpl extends ServiceImpl<SocializingMapper, SocializingDO> implements SocializingService {

    @Autowired
    private UserService userService;

    @Autowired
    private SocializingService socializingService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 判断token是否正确
     *
     * @param token 用户token
     * @return 返回结果
     */
    public boolean isRedisTokenExist(String token) {
        return redisTemplate.hasKey(JWTUtils.getIdByToken(token));
    }


    /**
     * 关注
     *
     * @param token 用户token
     * @param user  待关注的用户对象
     * @return 返回Result结果
     */
    @Override
    public Result<HashMap<String, Object>> save(String token, UserDO user) {
        // 判断token是否非空
        if (token.isEmpty()) {
            return Result.error("未接收到token");
        }
        // 判断token是否正确
        else if (isRedisTokenExist(token)) {
            Long uid = JWTUtils.getIdByToken(token);

            // 通过mybatisPlus查找是否已经关注该用户
            LambdaQueryWrapper<SocializingDO> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(SocializingDO::getId, uid);
            queryWrapper1.eq(SocializingDO::getFollowId, user.getId());
            if (socializingService.getOne(queryWrapper1) != null) {
                return Result.error("已关注此用户");
            }

            // 根据用户id查找该用户是否存在
            LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(UserDO::getId, user.getId());
            UserDO usr = userService.getOne(queryWrapper);
            if (usr == null) {
                return Result.error("该用户不存在");
            }

            // 添加关注
            SocializingDO socializing = new SocializingDO();
            socializing.setUserId(uid);
            socializing.setFollowId(user.getId());

            // 在表中添加关注
            socializingService.save(socializing);

            HashMap<String, Object> data = new HashMap<>();
            data.put("id", usr.getId());

            return Result.success(data, "成功关注！");
        } else {
            return Result.error("未登录");
        }
    }

    /**
     * 取关
     *
     * @param token 用户的token
     * @param user  待取关的用户信息
     * @return 返回Result结果
     */
    @Override
    public Result<HashMap<String, Object>> delete(String token, UserDO user) {
        // 判断token是否为空
        if (token.isEmpty()) {
            return Result.error("未接收到token");
        }
        // 判断token是否正确
        else if (isRedisTokenExist(token)) {
            // 判断是否存在该关注信息
            LambdaQueryWrapper<SocializingDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SocializingDO::getUserId, JWTUtils.getIdByToken(token)).eq(SocializingDO::getFollowId, user.getId());
            SocializingDO social = socializingService.getOne(queryWrapper);
            if (social == null) {
                return Result.error("没有此条关注数据。");
            }

            //删除该关注信息
            socializingService.remove(queryWrapper);

            HashMap<String, Object> data = new HashMap<>();
            data.put("id", social.getId());

            return Result.success(data, "删除成功");
        } else {
            return Result.error("未登录");
        }
    }

    /**
     * 查看关注列表
     *
     * @param token 用户token
     * @return 返回关注列表
     */
    @Override
    public Result<List<Long>> getFollows(String token) {
        // 判断token是否为空
        if (token.isEmpty()) {
            return Result.error("未接收到token");
        }
        // 判断token是否正确
        else if (isRedisTokenExist(token)) {
            //查找该用户的关注人id
            LambdaQueryWrapper<SocializingDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SocializingDO::getUserId, JWTUtils.getIdByToken(token));
            List<SocializingDO> socials = socializingService.list(queryWrapper);
            List<Long> ids = new ArrayList<>();
            for (SocializingDO social : socials) {
                ids.add(social.getFollowId());
            }
            return Result.success(ids, "查找成功!");
        } else {
            return Result.error("未登录");
        }
    }

    /**
     * 查看粉丝列表
     *
     * @param token 用户的token
     * @return 返回粉丝列表
     */
    @Override
    public Result<List<Long>> getFans(String token) {
        // 判断token是否为空
        if (token.isEmpty()) {
            return Result.error("未接收到token");
        } else if (isRedisTokenExist(token)) {
            //查找该用户的粉丝id
            LambdaQueryWrapper<SocializingDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SocializingDO::getFollowId, JWTUtils.getIdByToken(token));
            List<SocializingDO> socials = socializingService.list(queryWrapper);
            List<Long> ids = new ArrayList<>();
            for (SocializingDO social : socials) {
                ids.add(social.getUserId());
            }
            return Result.success(ids, "查找成功!");
        } else {
            return Result.error("未登录");
        }
    }

    /**
     * 查看朋友列表
     *
     * @param token 用户token
     * @return 返回朋友列表
     */
    @Override
    public Result<List<Long>> getFriends(String token) {
        if (token.isEmpty()) {
            return Result.error("未接收到token");
        } else if (isRedisTokenExist(token)) {
            ArrayList<Long> data = new ArrayList<>();
            //获取该用户的关注人
            List<Long> follows = getFollows(token).getData();
            for (Long follow : follows) {
                LambdaQueryWrapper<SocializingDO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SocializingDO::getUserId, follow);
                List<SocializingDO> socials = socializingService.list(queryWrapper);
                for (SocializingDO social : socials) {
                    //判断该用户关注人的关注人是不是自己（互关）
                    if (social.getFollowId().equals(JWTUtils.getIdByToken(token))) {
                        data.add(follow);
                    }
                }
            }
            return Result.success(data, "查找成功！");
        } else {
            return Result.error("未登录");
        }
    }
}

