package com.jiang.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_comment")
public class CommentDO {
    /**
     * 评论id
     */
    private Long id;

    /**
     *评论的父id
     */
    private Long fatherId;

    /**
     *视频id
     */
    private Long videoId;

    /**
     *创建时间
     */
    private Date creatTime;
}
