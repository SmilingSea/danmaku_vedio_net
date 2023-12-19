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

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/video")
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    public Result<HashMap<String, Object>> save(@RequestHeader String token, @RequestParam("video") MultipartFile file, @RequestParam("title")String title, @RequestParam("type")String type) throws IOException {
        return videoService.save(token,file,title,type);
    }

    @PostMapping("/click")
    public Result<HashMap<String, Object>> click( @RequestBody VideoDO video){
        return videoService.click(video);
    }

    @GetMapping("/rank")
    public Result<HashMap<String , Object>> getRank(){
        return videoService.getRank();
    }

}
