package xyz.funnyboy.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.model.system.SysRole;
import xyz.funnyboy.vo.system.AssignRoleVO;

import java.util.Map;

public interface SysRoleService extends IService<SysRole>
{
    /**
     * 根据用户获取角色数据
     *
     * @param userId
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> findRoleByUserId(Long userId);

    /**
     * 分配角色
     *
     * @param assignRoleVO
     */
    void doAssign(AssignRoleVO assignRoleVO);
}
