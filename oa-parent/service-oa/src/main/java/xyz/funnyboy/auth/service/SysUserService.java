package xyz.funnyboy.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.model.system.SysUser;

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
}
