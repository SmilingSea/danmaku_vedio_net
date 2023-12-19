package com.jiang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.common.Result;
import com.jiang.domain.VideoDO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

public interface VideoService extends IService<VideoDO> {
    public Result<HashMap<String,Object>> save(String token,MultipartFile file, String title, String type) throws IOException;

    public Result<HashMap<String,Object>> click(VideoDO video);

    Result<HashMap<String, Object>> getRank();
}
