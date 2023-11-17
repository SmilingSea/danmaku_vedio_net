package com.jiang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.domain.VideoDO;
import com.jiang.mapper.VideoMapper;
import com.jiang.service.VideoService;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, VideoDO> implements VideoService {
}
