package com.jiang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.domain.SocializingDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;

@Mapper
public interface SocializingMapper extends BaseMapper<SocializingDO> {
}
