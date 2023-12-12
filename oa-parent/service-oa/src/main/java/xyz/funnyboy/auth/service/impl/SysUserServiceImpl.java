package xyz.funnyboy.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.auth.mapper.SysUserMapper;
import xyz.funnyboy.auth.service.SysUserService;
import xyz.funnyboy.model.system.SysUser;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService
{
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
}
