package com.jiang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.domain.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author SmlingSea
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}
