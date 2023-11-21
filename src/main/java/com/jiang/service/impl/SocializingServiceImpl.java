package com.jiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.common.Result;
import com.jiang.domain.SocializingDO;
import com.jiang.domain.UserDO;
import com.jiang.mapper.SocializingMapper;
import com.jiang.service.SocializingService;
import com.jiang.service.UserService;
import com.jiang.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SocializingServiceImpl extends ServiceImpl<SocializingMapper, SocializingDO> implements SocializingService {

    @Autowired
    private UserService userService;

    @Autowired
    private SocializingService socializingService;


    @Override
    public Result<HashMap<String, Object>> save(String token, UserDO user) {
        Long uid = JwtUtils.getIdByToken(token);

        // TODO:先判断Socail表中是否存在该条数据

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
    }

    @Override
    public Result<HashMap<String, Object>> delete(String token, UserDO user) {
        //判断是否存在该关注信息
        LambdaQueryWrapper<SocializingDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SocializingDO::getUserId, JwtUtils.getIdByToken(token)).or()
                .eq(SocializingDO::getFollowId, user.getId());
        SocializingDO social = socializingService.getOne(queryWrapper);
        if (social == null) {
            return Result.error("没有此条关注数据。");
        }

        //删除该消息
        socializingService.remove(queryWrapper);

        HashMap<String, Object> data = new HashMap<>();
        data.put("id", social.getId());

        return Result.success(data, "删除成功");
    }

    @Override
    public Result<List<Long>> getFollows(String token) {

        //查找该用户的关注人id
        LambdaQueryWrapper<SocializingDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SocializingDO::getUserId, JwtUtils.getIdByToken(token));
        List<SocializingDO> socials = socializingService.list(queryWrapper);
        List<Long> ids = new ArrayList<>();
        for (SocializingDO social : socials) {
            ids.add(social.getFollowId());
        }


        return Result.success(ids, "查找成功!");
    }

    @Override
    public Result<List<Long>> getFans(String token) {

        //查找该用户的粉丝id
        LambdaQueryWrapper<SocializingDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SocializingDO::getFollowId, JwtUtils.getIdByToken(token));
        List<SocializingDO> socials = socializingService.list(queryWrapper);
        List<Long> ids = new ArrayList<>();
        for (SocializingDO social : socials) {
            ids.add(social.getUserId());
        }
        return Result.success(ids, "查找成功!");

    }

    @Override
    public Result<List<Long>> getFriends(String token) {
        ArrayList<Long> data = new ArrayList<>();
        //获取该用户的关注人
        List<Long> follows = getFollows(token).getData();
        for (Long follow : follows) {
            LambdaQueryWrapper<SocializingDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SocializingDO::getUserId,follow);
            List<SocializingDO> socials = socializingService.list(queryWrapper);
            for (SocializingDO social : socials) {
                //判断该用户关注人的关注人是不是自己（互关）
                if (social.getFollowId().equals(JwtUtils.getIdByToken(token))){
                    data.add(follow);
                }
            }
        }
        return Result.success(data,"查找成功！");
    }
}
