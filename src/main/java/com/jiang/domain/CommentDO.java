package com.jiang.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_comment")
public class CommentDO {
    /**
     * 评论id
     */
    @TableField(value = "id")
    private Long id;

    /**
     * 评论的父id
     */
    @TableField(value = "father_id")
    private Long fatherId;

    /**
     * 视频id
     */
    @TableField(value = "video_id")
    private Long videoId;
    /**
     * 发表评论的用户的id
     */
    @TableField(value = "user_id")
    private Long userId;
    /**
     * 评论的内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;
}
