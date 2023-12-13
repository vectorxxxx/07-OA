package xyz.funnyboy.vo.system;

import lombok.Getter;
import lombok.Setter;

/**
 * 登录对象
 */
@Getter
@Setter
public class LoginVO
{

    /**
     * 手机号
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
