package xyz.funnyboy.wechat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.model.wechat.Menu;
import xyz.funnyboy.vo.wechat.MenuVO;

import java.util.List;

/**
 * @author VectorX
 * @version V1.0
 * @description 菜单管理Service
 * @date 16/12/2023
 */
public interface MenuService extends IService<Menu>
{
    /**
     * 查找菜单信息
     *
     * @return {@link List}<{@link MenuVO}>
     */
    List<MenuVO> findMenuInfo();

    /**
     * 推送菜单
     */
    void syncMenu();

    /**
     * 删除菜单
     */
    void removeMenu();
}
