package com.jiang.controller;

import com.jiang.common.Result;
import com.jiang.domain.CommentDO;
import com.jiang.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 评论模块接口
 * @author SmilingSea
 */

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    /**
     * 添加评论
     * @param token 登录用户的token
     * @param comment 评论内容
     * @return 返回Result对象
     */
    @PostMapping
    public Result<HashMap<String,Object>> save(@RequestHeader String token, @RequestBody CommentDO comment){
        return commentService.save(token,comment);
    }
}
