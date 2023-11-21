package com.jiang.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
