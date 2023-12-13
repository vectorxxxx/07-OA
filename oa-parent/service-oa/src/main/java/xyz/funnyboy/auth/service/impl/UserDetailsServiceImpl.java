package xyz.funnyboy.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import xyz.funnyboy.auth.service.SysUserService;
import xyz.funnyboy.model.system.SysUser;
import xyz.funnyboy.security.custom.CustomUser;

import java.util.Collections;

@Component
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final SysUser sysUser = sysUserService.getByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户不存在！");
        }
        if (sysUser.getStatus() == 0) {
            throw new RuntimeException("账号已停用！");
        }
        return new CustomUser(sysUser, Collections.emptyList());
    }
}
