package xyz.funnyboy.security.custom;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import xyz.funnyboy.model.system.SysUser;

import java.util.Collection;

/**
 * @author VectorX
 * @version 1.0.0
 * @description 用户对象
 * @date 2023/12/13
 */
@Getter
@Setter
public class CustomUser extends User
{
    /**
     * 我们自己的用户实体对象，要调取用户信息时直接获取这个实体对象。（这里我就不写get/set方法了）
     */
    private SysUser sysUser;

    public CustomUser(SysUser sysUser, Collection<? extends GrantedAuthority> authorities) {
        super(sysUser.getUsername(), sysUser.getPassword(), authorities);
        this.sysUser = sysUser;
    }
}
