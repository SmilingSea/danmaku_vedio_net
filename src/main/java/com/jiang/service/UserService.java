package com.jiang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.common.Result;
import com.jiang.domain.UserDO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

public interface UserService extends IService<UserDO> {
    /**
     * 用户注册
     *
     * @param request
     * @param user
     * @return
     */
    public Result<HashMap<String, Object>> save(HttpServletRequest request, UserDO user);

    public Result<String> login(/*UserDO user*/ String name,String password);

    public Result<UserDO> getById(String token);

    public UserDO getByUserName(String name);

    public Result<HashMap<String, Object>> saveAvatar(String token, MultipartFile file) throws IOException;
}
