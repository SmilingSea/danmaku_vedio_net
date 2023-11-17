package com.jiang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.common.Result;
import com.jiang.domain.UserDO;
import com.jiang.mapper.UserMapper;
import com.jiang.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Autowired
    private UserService userService;


    @Override
    public Result<HashMap<String, Object>> save(HttpServletRequest request, UserDO user) {
        log.info("新增员工，员工信息为：{}"+user.toString());
        //设置使用md5进行加密密码
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));

        //获取当前用户id
        Long userId = (Long) request.getSession().getAttribute("user");

        userService.save(user);
        HashMap<String, Object> data = new HashMap<>();
       data.put("id", user.getId());
        return Result.success(data,"注册成功！");
    }

    @Override
    public Result<String> login(String name, String password) {
        return null;
    }
}
