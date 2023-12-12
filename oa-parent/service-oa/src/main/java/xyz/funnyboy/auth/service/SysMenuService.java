package xyz.funnyboy.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.model.system.SysMenu;
import xyz.funnyboy.vo.system.AssignMenuVO;

import java.io.Serializable;
import java.util.List;

public interface SysMenuService extends IService<SysMenu>
{
    /**
     * 菜单树形数据
     *
     * @return {@link List}<{@link SysMenu}>
     */
    List<SysMenu> findNodes();

    /**
     * 删除菜单
     *
     * @param id
     * @return boolean
     */
    @Override
    boolean removeById(Serializable id);

    /**
     * 根据角色获取菜单
     *
     * @param roleId
     * @return {@link List}<{@link SysMenu}>
     */
    List<SysMenu> findSysMenuByRoleId(Long roleId);

    /**
     * 给角色分配菜单
     *
     * @param assignMenuVO
     */
    void doAssign(AssignMenuVO assignMenuVO);
}
