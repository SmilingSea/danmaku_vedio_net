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

import java.util.HashMap;

@Service
public class SocializingServiceImpl extends ServiceImpl<SocializingMapper, SocializingDO> implements SocializingService {

    @Autowired
    private UserService userService;

    @Autowired
    private SocializingService socializingService;


    @Override
    public Result<HashMap<String,Object>> save(String token, UserDO user) {
        Long uid = JwtUtils.getIdByToken(token);

        // TODO:先判断Socail表中是否存在该条数据

        // 根据用户id查找该用户是否存在
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getId,user.getId());
        UserDO usr = userService.getOne(queryWrapper);
        if(usr == null){
            return Result.error("该用户不存在");
        }

        // 添加关注
        SocializingDO socializing = new SocializingDO();
        socializing.setUserId(uid);
        socializing.setFollowId(user.getId());

        // 在表中添加关注
        socializingService.save(socializing);

        HashMap<String, Object> data = new HashMap<>();
        data.put("id",usr.getId());

        return Result.success(data,"成功关注！");
    }

    @Override
    public Result<HashMap<String, Object>> delete(String token,UserDO user) {
        //判断是否存在该关注信息
        LambdaQueryWrapper<SocializingDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SocializingDO::getUserId,JwtUtils.getIdByToken(token)).or()
                .eq(SocializingDO::getFollowId,user.getId());
        SocializingDO social = socializingService.getOne(queryWrapper);
        if (social == null){
            return Result.error("没有此条关注数据。");
        }

        //删除该消息
        socializingService.remove(queryWrapper);

        HashMap<String, Object> data = new HashMap<>();
        data.put("id",social.getId());

        return Result.success(data,"删除成功");
    }

    @Override
    public Result<HashMap<String, Object>> getFollows(String token) {

        //查找该用户的关注人id
        LambdaQueryWrapper<SocializingDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SocializingDO::getUserId,JwtUtils.getIdByToken(token));
        SocializingDO social = socializingService.getOne(queryWrapper);
        Long followId = social.getFollowId();

        LambdaQueryWrapper<UserDO> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(UserDO::getId,followId);
        UserDO user = userService.getOne(queryWrapper1);

        HashMap<String, Object> data = new HashMap<>();
        data.put("name",user.getName());


        return Result.success(data,"查找成功!");
    }
}
