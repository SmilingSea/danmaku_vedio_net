package com.jiang.controller;

import com.jiang.common.Result;
import com.jiang.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/video/{key}")
    public Result<HashMap<String , Object>> searchVideo(@RequestHeader String token, @PathVariable String key){
        return searchService.searchVideo(token,key);
    }

    @GetMapping("/user/{key}")
    public Result<HashMap<String, Object>> searchUser(@RequestHeader String token,@PathVariable String key){
        return searchService.searchUser(token, key);
    }

    @GetMapping("/log")
    public Result<HashMap<String, Object>> getSearchList(@RequestHeader String token){
        return searchService.getSearchList(token);
    }
}
