package com.jiang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.domain.CommentDO;
import com.jiang.mapper.CommentMapper;
import com.jiang.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, CommentDO> implements CommentService {
}
