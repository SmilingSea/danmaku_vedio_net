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

@Component
@AllArgsConstructor
public class CustomUserServiceImpl implements UserDetailsService {

    private  UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO user = userService.getByUserName(username);
        if(user == null){
            return null;
        }
        return new User(user.getName(),user.getPassword(), AuthorityUtils.createAuthorityList(user.getIdentity()));
    }
}
