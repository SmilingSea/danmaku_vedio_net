package com.jiang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.domain.SocializingDO;
import com.jiang.mapper.SocializingMapper;
import com.jiang.service.SocializingService;
import org.springframework.stereotype.Service;

@Service
public class SocializingServiceImpl extends ServiceImpl<SocializingMapper, SocializingDO> implements SocializingService {
}
