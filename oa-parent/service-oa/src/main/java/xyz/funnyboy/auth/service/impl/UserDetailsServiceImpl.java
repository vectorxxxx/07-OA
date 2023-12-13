package xyz.funnyboy.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import xyz.funnyboy.auth.service.SysMenuService;
import xyz.funnyboy.auth.service.SysUserService;
import xyz.funnyboy.model.system.SysUser;
import xyz.funnyboy.security.custom.CustomUser;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 根据用户名加载用户信息
     *
     * @param username 用户名
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户信息
        final SysUser sysUser = sysUserService.getByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户不存在！");
        }
        if (sysUser.getStatus() == 0) {
            throw new RuntimeException("账号已停用！");
        }

        // 根据用户ID获取用户权限
        final List<String> userPermsList = sysMenuService.findUserPermsList(sysUser.getId());
        final List<SimpleGrantedAuthority> authorities = userPermsList.stream()
                                                                      .map(perm -> new SimpleGrantedAuthority(perm.trim()))
                                                                      .collect(Collectors.toList());

        // 组装用户信息和权限信息到自定义认证对象中
        return new CustomUser(sysUser, authorities);
    }
}
