package com.jiang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.domain.VideoDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VideoMapper extends BaseMapper<VideoDO> {
}
