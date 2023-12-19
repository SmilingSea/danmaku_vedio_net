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

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public Result<HashMap<String,Object>> save(HttpServletRequest request, @RequestBody UserDO user){
        return userService.save(request, user);
    }

    @PostMapping("/login")
    public Result<String> login(/*@RequestBody UserDO user*/ String name ,String password){
        return userService.login( name,  password);
    }

    @GetMapping
    public Result<UserDO> getById(@RequestHeader String token){
        return  userService.getById(token);
    }

    @PostMapping("avatar")
    public Result<HashMap<String,Object>> saveAvatar(@RequestHeader String token, @RequestParam("avatar")MultipartFile file) throws IOException {
        return userService.saveAvatar(token,file);
    }
}
