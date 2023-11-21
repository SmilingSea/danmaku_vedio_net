package com.jiang.controller;

import com.jiang.common.Result;
import com.jiang.domain.UserDO;
import com.jiang.service.SocializingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/socialising")
public class SocializingController {

    private final SocializingService socializingService;

    @PostMapping("/follow")
    public Result<HashMap<String,Object>> save(@RequestHeader String token, @RequestBody UserDO usr){
        return socializingService.save(token,usr);
    }

    @DeleteMapping
    public Result<HashMap<String, Object>> delete(@RequestHeader String token, @RequestBody UserDO usr){
        return socializingService.delete(token,usr);
    }

    @GetMapping("/{token}")
    public Result<HashMap<String, Object>> getFollows(@PathVariable String token){
        return socializingService.getFollows(token);
    }

}
