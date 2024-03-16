package com.jiang.controller;

import com.jiang.common.Result;
import com.jiang.domain.UserDO;
import com.jiang.service.SocializingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 社交模块接口
 * @author SmilingSea
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/socialising")
public class SocializingController {

    private final SocializingService socializingService;

    /**
     * 关注
     * @param token 用户的token
     * @param usr 待关注用户对象
     * @return 返回Result对象
     */
    @PostMapping("/follow")
    public Result<HashMap<String,Object>> save(@RequestHeader String token, @RequestBody UserDO usr){
        return socializingService.save(token,usr);
    }

    /**
     * 取消关注
     * @param token 用户的token
     * @param usr 待取关的用户对象
     * @return 返回的Result对象
     */
    @DeleteMapping
    public Result<HashMap<String, Object>> delete(@RequestHeader String token, @RequestBody UserDO usr){
        return socializingService.delete(token,usr);
    }

    /**
     * 获取关注列表
     * @param token 用户的token
     * @return 返回Result对象
     */
    @GetMapping("/follow")
    public Result<List<Long>> getFollows(@RequestHeader String token){
        return socializingService.getFollows(token);
    }

    /**
     * 获取粉丝列表
     * @param token 用户的token
     * @return 返回Result对象
     */
    @GetMapping("/fans")
    public Result<List<Long>> getFans(@RequestHeader String token){
        return socializingService.getFans(token);
    }

    /**
     * 获取好友列表
     * @param token 用户的token
     * @return 返回Result对象
     */
    @GetMapping("/friends")
    public Result<List<Long>> getFriends(@RequestHeader String token){
        return socializingService.getFriends(token);
    }
}
