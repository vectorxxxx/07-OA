package xyz.funnyboy.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.funnyboy.auth.mapper.SysUserMapper;
import xyz.funnyboy.auth.service.SysMenuService;
import xyz.funnyboy.auth.service.SysUserService;
import xyz.funnyboy.model.system.SysUser;
import xyz.funnyboy.vo.system.RouterVO;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService
{
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return boolean
     */
    @Override
    public boolean updateStatus(Long id, Integer status) {
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setStatus(status);
        return this.updateById(sysUser);
    }

    /**
     * 根据用户名获取用户
     *
     * @param username
     * @return {@link SysUser}
     */
    @Override
    public SysUser getByUsername(String username) {
        return this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
    }

    /**
     * 根据用户名获取用户登录信息
     *
     * @param username
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> getUserInfo(String username) {
        // 基础信息
        final SysUser sysUser = this.getByUsername(username);
        final Long userId = sysUser.getId();
        final String userName = sysUser.getName();

        // 根据用户id获取菜单权限
        final List<RouterVO> routers = sysMenuService.findUserMenuList(userId);
        // 根据用户id获取按钮权限
        final List<String> buttons = sysMenuService.findUserPermsList(userId);

        // 组装登录信息
        Map<String, Object> result = new HashMap<>();
        result.put("name", userName);
        result.put("avatar", "https://crowdfunding-vectorx.oss-cn-shanghai.aliyuncs.com/20231210/1.png");
        result.put("roles", new HashSet<>());
        result.put("routers", routers);
        result.put("buttons", buttons);
        return result;
    }
}
