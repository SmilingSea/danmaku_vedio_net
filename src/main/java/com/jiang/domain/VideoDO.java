package com.jiang.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

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
//    private Enum<> type;

    /**
     *视频点击量
     */
    private int clicknum;
}
