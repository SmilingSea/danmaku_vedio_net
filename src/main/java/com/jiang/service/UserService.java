package com.jiang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.common.Result;
import com.jiang.domain.UserDO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author SmilingSea
 */
public interface UserService extends IService<UserDO> {

    Result<HashMap<String, Object>> save(HttpServletRequest request, UserDO user);

    Result<String> login(String name, String password);

    Result<UserDO> getById(String token);

    Result<HashMap<String, Object>> saveAvatar(String token, MultipartFile file) throws IOException;
}
