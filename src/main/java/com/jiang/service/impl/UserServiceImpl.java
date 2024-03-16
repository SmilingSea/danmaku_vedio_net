package com.jiang.service.impl;

import com.alibaba.fastjson.JSON;
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
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.spel.CodeFlow;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * 用户模块的实现类
 *
 * @author SmilingSea
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 用户注册
     *
     * @param request http请求
     * @param user    用户对象
     * @return Result结果
     */
    @Override
    public Result<HashMap<String, Object>> save(HttpServletRequest request, UserDO user) {
        try {
            //设置使用md5进行加密密码
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));

            //检查用户名是否重复
            LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(UserDO::getName, user.getName());
            if (userService.getOne(queryWrapper) != null) {
                return Result.error("该用户已存在...");
            }

            userService.save(user);

            // 将数据同步上传到es索引库
            String json = JSON.toJSONString(user);
            // 1.准备request对象
            IndexRequest request1 = new IndexRequest("user").id(user.getId().toString());
            // 2.准备json文档
            request1.source(json, XContentType.JSON);
            // 3.发送请求
            client.index(request1, RequestOptions.DEFAULT);

            HashMap<String, Object> data = new HashMap<>();
            data.put("id", user.getId());
            return Result.success(data, "注册成功！");
        } catch (Exception e) {
            throw new RuntimeException();
        }


    }

    /**
     * 用户登录
     *
     * @param name     用户名
     * @param password 密码
     * @return 返回Result对象
     */
    @Override
    public Result<String> login(String name, String password) {
        // 转换密码
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //根据用户名name查询数据库
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getName, name);
        UserDO usr = userService.getOne(queryWrapper);

        //如果没有查询到则返回失败
        if (usr == null) {
            return Result.error("登录失败");
        }

        //比对密码，如果不一致则返回登录失败
        if (!usr.getPassword().equals(password)) {
            return Result.error("登录失败");
        }

        // 获取token
        String token = JWTUtils.getToken(usr);

        //将token和id存入reids
        redisTemplate.opsForValue().set(JWTUtils.getIdByToken(token), token, 7, TimeUnit.DAYS);

        //登录成功，返回token
        return Result.success(token, "登录成功！");
    }

    /**
     * 根据用户的token转换成id查找用户
     *
     * @param token 用户token
     * @return 返回用户信息（包含敏感信息）
     */
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


    /**
     * 上传用户头像
     *
     * @param token 用户token
     * @param multipartFile 头像的图片文件
     * @return 返回Result结果
     * @throws IOException 腾讯云COS抛出的异常
     */
    @Override
    public Result<HashMap<String, Object>> saveAvatar(String token, MultipartFile multipartFile) throws IOException {
        if (token.isEmpty()) {
            return Result.error("未接收到token");
        }
        Long usrid = JWTUtils.getIdByToken(token);

        // 将MultipartFile类型转换成File类型
        File file = File.createTempFile(String.valueOf(IdWorker.getId()), ".png");
        multipartFile.transferTo(file);

        // 将头像文件上传到腾讯云
        String url = COSUtils.UploadAvatar(file);

        // 将信息保存到数据库
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getId, usrid);
        UserDO user = new UserDO();
        user.setAvatar(url);
        user.setId(usrid);
        userService.updateById(user);

        HashMap<String, Object> data = new HashMap<>();
        data.put("url", url);

        return Result.success(data, "上传成功");
    }
}
