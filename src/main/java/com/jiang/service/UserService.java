package com.jiang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.common.Result;
import com.jiang.domain.UserDO;

import javax.servlet.http.HttpServletRequest;
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

    public Result<String> login(UserDO user);

    public Result<UserDO> getById(String token);

    //TODO:用户的身份认证，安全框架
    //TODO:头像功能


}
