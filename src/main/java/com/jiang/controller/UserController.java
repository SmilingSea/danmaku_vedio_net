package com.jiang.controller;

import com.jiang.common.Result;
import com.jiang.domain.UserDO;
import com.jiang.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

/**
 * 用户模块接口
 * @author SmilingSea
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    /**
     * 用户注册
     * @param request 处理http请求
     * @param user 要注册的user对象
     * @return 返回Result对象
     */
    @PostMapping
    public Result<HashMap<String,Object>> save(HttpServletRequest request, @RequestBody UserDO user){
        return userService.save(request, user);
    }

    /**
     * 用户登录
     * @param name 用户名
     * @param password 密码
     * @return 返回Result对象
     */
    @PostMapping("/login")
    public Result<String> login(/*@RequestBody UserDO user*/ String name ,String password){
        return userService.login( name,  password);
    }

    /**
     * 查看用户信息（自身查看）
     * @param token 用户token
     * @return 返回Result对象
     */
    @GetMapping
    public Result<UserDO> getById(@RequestHeader String token){
        return  userService.getById(token);
    }

    /**
     * 上传头像
     * @param token 用户token
     * @param file 上传的头像文件
     * @return 返回Result对象
     * @throws IOException 腾讯云对象存储抛出异常
     */
    @PostMapping("avatar")
    public Result<HashMap<String,Object>> saveAvatar(@RequestHeader String token, @RequestParam("avatar")MultipartFile file) throws IOException {
        return userService.saveAvatar(token,file);
    }
}
