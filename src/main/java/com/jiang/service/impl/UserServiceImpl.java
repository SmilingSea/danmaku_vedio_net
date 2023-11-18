package com.jiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.common.Result;
import com.jiang.domain.UserDO;
import com.jiang.mapper.UserMapper;
import com.jiang.service.UserService;
import com.jiang.util.TokenCreater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Autowired
    private UserService userService;


    @Override
    public Result<HashMap<String, Object>> save(HttpServletRequest request, UserDO user) {
        log.info("新增员工，员工信息为：{}"+user.toString());
        //设置使用md5进行加密密码
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));

        //获取当前用户id
        Long userId = (Long) request.getSession().getAttribute("user");

        userService.save(user);
        HashMap<String, Object> data = new HashMap<>();
       data.put("id", user.getId());
        return Result.success(data,"注册成功！");
    }

    @Override
    public Result<String> login(UserDO user) {
        //将提交的密码password进行MD5加密处理
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //根据用户名name查询数据库
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getName, user.getName());
        UserDO usr = userService.getOne(queryWrapper);

        //如果没有查询到则返回失败
        if(usr == null){
            return Result.error("登录失败");
        }

        //比对密码，如果不一致则返回登录失败
        if(!usr.getPassword().equals(password)){
            return Result.error("登录失败");
        }

        //登录成功，返回token
        return Result.success(TokenCreater.getToken(usr),"登录成功！");
    }
}
