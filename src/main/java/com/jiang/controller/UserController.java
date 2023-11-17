package com.jiang.controller;

import com.jiang.common.Result;
import com.jiang.domain.UserDO;
import com.jiang.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
//    @Api
    public Result<HashMap<String,Object>> save(HttpServletRequest request, @RequestBody UserDO user){
        return userService.save(request, user);
    }
}
