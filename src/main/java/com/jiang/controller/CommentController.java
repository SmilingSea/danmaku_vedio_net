package com.jiang.controller;

import com.jiang.common.Result;
import com.jiang.domain.CommentDO;
import com.jiang.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public Result<HashMap<String,Object>> save(@RequestHeader String token, @RequestBody CommentDO comment){
        return commentService.save(token,comment);
    }
}
