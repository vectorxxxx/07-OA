package xyz.funnyboy.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.funnyboy.auth.mapper.SysMenuMapper;
import xyz.funnyboy.auth.service.SysMenuService;
import xyz.funnyboy.auth.service.SysRoleMenuService;
import xyz.funnyboy.auth.util.MenuHelper;
import xyz.funnyboy.common.handler.VectorXException;
import xyz.funnyboy.model.system.SysMenu;
import xyz.funnyboy.model.system.SysRoleMenu;
import xyz.funnyboy.vo.system.AssignMenuVO;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService
{
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 菜单树形数据
     *
     * @return {@link List}<{@link SysMenu}>
     */
    @Override
    public List<SysMenu> findNodes() {
        // 全部权限列表
        final List<SysMenu> menuList = this.list();
        if (CollectionUtils.isEmpty(menuList)) {
            return null;
        }

        // 构建树形结构
        return MenuHelper.buildTree(menuList);
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return boolean
     */
    @Override
    public boolean removeById(Serializable id) {
        int count = this.count(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, id));
        if (count > 0) {
            throw new VectorXException(501, "菜单不能删除");
        }
        final int delete = this.getBaseMapper()
                               .deleteById(id);
        return delete > 0;
    }

    /**
     * 根据角色获取菜单
     *
     * @param roleId
     * @return {@link List}<{@link SysMenu}>
     */
    @Override
    public List<SysMenu> findSysMenuByRoleId(Long roleId) {
        // 查询所有启用的菜单
        final List<SysMenu> allMenuList = this.list(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus, 1));
        if (CollectionUtils.isEmpty(allMenuList)) {
            return null;
        }

        // 查询角色拥有的菜单
        final List<Long> menuIdList = sysRoleMenuService.list(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId))
                                                        .stream()
                                                        .map(SysRoleMenu::getMenuId)
                                                        .collect(Collectors.toList());

        // 构建树形结构
        allMenuList.forEach(permission -> {
            permission.setSelect(menuIdList.contains(permission.getId()));
        });
        return MenuHelper.buildTree(allMenuList);
    }

    /**
     * 给角色分配菜单
     *
     * @param assignMenuVO
     */
    @Override
    public void doAssign(AssignMenuVO assignMenuVO) {
        final Long roleId = assignMenuVO.getRoleId();
        final List<Long> menuIdList = assignMenuVO.getMenuIdList();
        final Function<Long, SysRoleMenu> function = menuId -> {
            final SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(menuId);
            return sysRoleMenu;
        };
        final List<SysRoleMenu> roleMenuList = menuIdList.stream()
                                                         .map(function)
                                                         .collect(Collectors.toList());
        sysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
        sysRoleMenuService.saveBatch(roleMenuList);
    }
}
