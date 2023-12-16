package xyz.funnyboy.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.model.system.SysUser;

import java.util.Map;

public interface SysUserService extends IService<SysUser>
{
    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return boolean
     */
    boolean updateStatus(Long id, Integer status);

    /**
     * 根据用户名获取用户
     *
     * @param username
     * @return {@link SysUser}
     */
    SysUser getByUsername(String username);

    /**
     * 根据用户名获取用户登录信息
     *
     * @param username
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> getUserInfo(String username);

    /**
     * 获取当前用户
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> getCurrentUser();
}
