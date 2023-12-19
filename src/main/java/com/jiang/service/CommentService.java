package com.jiang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.common.Result;
import com.jiang.domain.CommentDO;

import java.util.HashMap;

public interface CommentService extends IService<CommentDO> {
    public Result<HashMap<String,Object>> save(String token,CommentDO comment);
}
