package xyz.funnyboy.security.custom;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import xyz.funnyboy.common.util.MD5;

/**
 * @author VectorX
 * @version 1.0.0
 * @description 密码处理
 * @date 2023/12/13
 */
@Component
public class CustomMd5PasswordEncoder implements PasswordEncoder
{
    @Override
    public String encode(CharSequence rawPassword) {
        return MD5.encrypt(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(MD5.encrypt(rawPassword.toString()));
    }
}
