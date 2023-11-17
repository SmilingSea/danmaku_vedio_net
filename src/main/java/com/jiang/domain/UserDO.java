package com.jiang.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_user")
public class UserDO {
    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户手机号码
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户头像
     */
    private String profile;

    /**
     * 封禁状态
     */
    private int blocked;

    /**
     * 用户身份（是否为管理员）
     */
    private int identity;


}
