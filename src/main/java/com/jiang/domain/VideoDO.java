package com.jiang.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
/**
 * Video类用来表示视频的基本信息
 *
 * @author SmilingSea
 * @date 2023/12/21
 */
@Data
@TableName("tb_video")
public class VideoDO {
    /**
     * 视频id
     */
    private Long id;

    /**
     *视频标题
     */
    private String title;

    /**
     *视频url
     */
    private String url;
    /**
     *发布时间
     */
    private Date time;

    /**
     *视频类型
     */
    private String type;

    /**
     * 用户id
     */
    private Long usrid;
}
