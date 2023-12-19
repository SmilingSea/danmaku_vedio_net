package com.jiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.common.Result;
import com.jiang.domain.UserDO;
import com.jiang.mapper.UserMapper;
import com.jiang.service.UserService;
import com.jiang.util.COSUtils;
import com.jiang.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public Result<HashMap<String, Object>> save(HttpServletRequest request, UserDO user) {

        // TODO:判断是否账号密码重复
        //设置使用md5进行加密密码
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));


        userService.save(user);
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", user.getId());
        return Result.success(data, "注册成功！");
    }

    @Override
    public Result<String> login(/*UserDO user*/ String name,String password) {

        //UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword());
       // Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        //将提交的密码password进行MD5加密处理
//        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //根据用户名name查询数据库
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getName, /*user.getName()*/name);
        UserDO usr = userService.getOne(queryWrapper);

        //如果没有查询到则返回失败
        if (usr == null) {
            return Result.error("登录失败");
        }

        //比对密码，如果不一致则返回登录失败
        if (!usr.getPassword().equals(password)) {
            return Result.error("登录失败");
        }

        String token = JWTUtils.getToken(usr);

        //将token和id存入reids
        redisTemplate.opsForValue().set(JWTUtils.getIdByToken(token),token,7, TimeUnit.DAYS);

        //登录成功，返回token
        return Result.success(token, "登录成功！");
    }

    @Override
    public Result<UserDO> getById(String token) {
        Long id = JWTUtils.getIdByToken(token);
        log.info("用户id为：{}", id);
        UserDO usr = userService.getById(id);
        if (usr != null) {
            return Result.success(usr, "查询成功！");
        }
        return Result.error("没有查到用户信息");
    }

    @Override
    public UserDO getByUserName(String name) {
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getName,name);
        UserDO user = userService.getOne(queryWrapper);
        if (user == null){
            return null;
        }

        return user;
    }

    @Override
    public Result<HashMap<String, Object>> saveAvatar(String token, MultipartFile multipartFile) throws IOException {
        if(token.isEmpty()){
            return Result.error("未接收到token");
        }
        Long usrid = JWTUtils.getIdByToken(token);

        File file = File.createTempFile(String.valueOf(IdWorker.getId()),".png");
        multipartFile.transferTo(file);

        String url = COSUtils.UploadAvatar(file);

        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getId,usrid);
        UserDO user = new UserDO();
        user.setAvatar(url);
        user.setId(usrid);
        userService.updateById(user);

        HashMap<String, Object> data = new HashMap<>();
        data.put("url",url);

        return Result.success(data,"上传成功");
    }


}
