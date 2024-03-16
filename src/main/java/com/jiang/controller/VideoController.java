package com.jiang.controller;

import com.jiang.common.Result;
import com.jiang.domain.VideoDO;
import com.jiang.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

/**
 * 视频模块接口
 * @author SmilingSea
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/video")
public class VideoController {

    private final VideoService videoService;

    /**
     * 上传视频
     * @param token 用户的token
     * @param file 上传的视频文件
     * @param title 视频的标题
     * @param type 视频类型
     * @return 返回Result对象
     * @throws IOException 对象存储抛出的异常
     */
    @PostMapping
    public Result<HashMap<String, Object>> save(@RequestHeader String token, @RequestParam("video") MultipartFile file, @RequestParam("title")String title, @RequestParam("type")String type) throws IOException {
        return videoService.save(token,file,title,type);
    }

    /**
     * 点击视频
     * @param video 点击的视频对象
     * @return 返回Result对象
     */
    @PostMapping("/click")
    public Result<HashMap<String, Object>> click( @RequestBody VideoDO video){
        return videoService.click(video);
    }

    /**
     * 点击量排行榜
     * @return 返回Result对象
     */
    @GetMapping("/rank")
    public Result<HashMap<String , Object>> getRank(){
        return videoService.getRank();
    }

}
