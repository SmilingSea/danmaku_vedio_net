package com.jiang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.domain.CommentDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author SmlingSea
 */
@Mapper
public interface CommentMapper extends BaseMapper<CommentDO> {
}
