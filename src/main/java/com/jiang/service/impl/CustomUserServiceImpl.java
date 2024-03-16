package com.jiang.service.impl;

import com.jiang.domain.UserDO;
import com.jiang.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 通过用户名查找用户SpringSecurity
 *
 * @author SmilingSea
 */
@Component
@AllArgsConstructor
public class CustomUserServiceImpl implements UserDetailsService {

    private UserService userService;

    /**
     * 通过用户名查找用户对象
     *
     * @param username 用户名
     * @return 用户信息
     * @throws UsernameNotFoundException 未找到用户时抛出异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 在数据库中按用户名查找用户
        UserDO user = userService.getByUserName(username);
        if (user == null) {
            return null;
        }
        return new User(user.getName(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getIdentity()));
    }
}
