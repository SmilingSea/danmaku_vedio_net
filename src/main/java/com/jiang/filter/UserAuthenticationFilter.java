package com.jiang.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户名和密码的过滤器Spring Security
 * @author SmilngSea
 */
public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private ThreadLocal<Map<String, String>> threadLocal = new ThreadLocal<>();

    /**
     * 获取密码
     * @param request 登录请求
     * @return 获取的密码
     */
    @Override
    protected String obtainPassword(HttpServletRequest request) {
        String password = this.getBodyParams(request).get(SPRING_SECURITY_FORM_PASSWORD_KEY);
        if (!StringUtils.isEmpty(password)){
            return password;
        }
        return super.obtainPassword(request);
    }

    /**
     * 获取用户名
     * @param request 登录请求
     * @return 获取的用户名
     */
    @Override
    protected String obtainUsername(HttpServletRequest request) {
        String username = this.getBodyParams(request).get("name");
        if (!StringUtils.isEmpty(username)){
            return username;
        }
        return super.obtainUsername(request);
    }

    /**
     * 获取body参数
     * @param request 登录请求
     * @return 返回body参数
     */
    private Map<String, String> getBodyParams(HttpServletRequest request) {
        Map<String, String> bodyParams = threadLocal.get();
        if (bodyParams == null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try (InputStream is = request.getInputStream()) {
                bodyParams = objectMapper.readValue(is, Map.class);
            } catch (IOException e) {
            }
            if (bodyParams == null) {
                bodyParams = new HashMap<>();
            }
            threadLocal.set(bodyParams);
        }
        return bodyParams;
    }


}
