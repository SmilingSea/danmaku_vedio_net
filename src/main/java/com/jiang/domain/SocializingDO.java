package com.jiang.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
 * SocializingDO 类用来表示用户之间的关注关系
 *
 * @author SmilingSea
 * @date 2023/12/21
 */
@Data
@TableName("tb_socializing")
public class SocializingDO {
    /**
     * 用户关系id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 该用户关注的人的id
     */
    private Long followId;
}
