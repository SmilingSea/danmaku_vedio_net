package com.jiang.controller;

import com.jiang.common.Result;
import com.jiang.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

/**
 * 搜索模块的接口
 * @author SmilingSea
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    /**
     * 搜索视频
     * @param token 用户的token
     * @param key 搜索的关键字
     * @return 返回Result对象
     */
    @GetMapping("/video")
    public Result<HashMap<String , Object>> searchVideo(@RequestHeader String token, @RequestParam String key) {
        return searchService.searchVideo(token,key);
    }

    /**
     * 搜索用户
     * @param token 用户的token
     * @param key 搜索的关键字
     * @return 返回Result对象
     */
    @GetMapping("/user")
    public Result<HashMap<String, Object>> searchUser(@RequestHeader String token,@RequestParam String key){
        return searchService.searchUser(token, key);
    }

    /**
     * 查询搜索记录
     * @param token 用户的token
     * @return 返回Result对象
     */
    @GetMapping("/log")
    public Result<HashMap<String, Object>> getSearchList(@RequestHeader String token){
        return searchService.getSearchList(token);
    }
}
