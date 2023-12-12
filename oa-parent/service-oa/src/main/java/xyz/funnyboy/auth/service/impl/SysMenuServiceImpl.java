package xyz.funnyboy.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.funnyboy.auth.mapper.SysMenuMapper;
import xyz.funnyboy.auth.service.SysMenuService;
import xyz.funnyboy.auth.util.MenuHelper;
import xyz.funnyboy.common.handler.VectorXException;
import xyz.funnyboy.model.system.SysMenu;

import java.io.Serializable;
import java.util.List;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService
{
    @Autowired
    private SysMenuMapper sysMenuMapper;

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
        final int delete = sysMenuMapper.deleteById(id);
        return delete > 0;
    }
}
