package xyz.funnyboy.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.model.system.SysMenu;

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
}
