package xyz.funnyboy.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import xyz.funnyboy.auth.mapper.SysMenuMapper;
import xyz.funnyboy.auth.service.SysMenuService;
import xyz.funnyboy.auth.service.SysRoleMenuService;
import xyz.funnyboy.auth.service.SysUserService;
import xyz.funnyboy.auth.util.MenuHelper;
import xyz.funnyboy.common.handler.VectorXException;
import xyz.funnyboy.model.system.SysMenu;
import xyz.funnyboy.model.system.SysRoleMenu;
import xyz.funnyboy.vo.system.AssignMenuVO;
import xyz.funnyboy.vo.system.RouterVO;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService
{
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysUserService sysUserService;

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

    @Override
    public List<RouterVO> findUserMenuList(Long userId) {
        List<SysMenu> menuList;
        final String username = sysUserService.getById(userId)
                                              .getUsername();
        // 管理员
        if ("admin".equals(username)) {
            menuList = this.list(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus, 1)
                                                                  .orderByAsc(SysMenu::getSortValue));
        }
        else {
            menuList = this.getBaseMapper()
                           .findMenuListByUserId(userId);
        }

        // 构建树形结构
        final List<SysMenu> treeList = MenuHelper.buildTree(menuList);

        // 构建路由
        return this.buildRouters(treeList);
    }

    /**
     * 获取用户按钮权限
     *
     * @param userId
     * @return {@link List}<{@link String}>
     */
    @Override
    public List<String> findUserPermsList(Long userId) {
        List<SysMenu> menuList;
        final String username = sysUserService.getById(userId)
                                              .getUsername();
        // 管理员
        if ("admin".equals(username)) {
            menuList = this.list(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus, 1));
        }
        else {
            menuList = this.getBaseMapper()
                           .findMenuListByUserId(userId);
        }

        return menuList.stream()
                       .filter(item -> item.getType() == 2)
                       .map(SysMenu::getPerms)
                       .collect(Collectors.toList());
    }

    /**
     * 根据菜单构建路由
     *
     * @param menuList
     * @return {@link List}<{@link RouterVO}>
     */
    private List<RouterVO> buildRouters(List<SysMenu> menuList) {
        final LinkedList<RouterVO> routers = new LinkedList<>();
        for (SysMenu menu : menuList) {
            RouterVO router = RouterVO.build(menu, false);
            final List<SysMenu> children = menu.getChildren();
            // 菜单
            if (menu.getType() == 1) {
                final List<SysMenu> hiddenMenuList = children.stream()
                                                             .filter(item -> !StringUtils.isEmpty(item.getComponent()))
                                                             .collect(Collectors.toList());
                for (SysMenu hiddenMenu : hiddenMenuList) {
                    RouterVO hiddenRouter = RouterVO.build(hiddenMenu, true);
                    routers.add(hiddenRouter);
                }
            }
            else if (!CollectionUtils.isEmpty(children)) {
                router.setAlwaysShow(true);
                router.setChildren(buildRouters(children));
            }
            routers.add(router);
        }
        return routers;
    }
}
