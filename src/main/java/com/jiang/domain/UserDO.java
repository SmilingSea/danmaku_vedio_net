package com.jiang.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
 * UserDO类用来表示该用户的基本信息
 *
 * @author SmilingSea
 * @date 2023/12/21
 */
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
    private String identity;

    /**
     * 用户头像url
     */
    private String avatar;


    public UserDO(Long id, String name, String password, String phone, String email, String profile, int blocked, String identity, String avatar) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.profile = profile;
        this.blocked = blocked;
        this.identity = identity;
        this.avatar = avatar;
    }

    public UserDO() {
    }
}
