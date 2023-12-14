package xyz.funnyboy.vo.system;

import lombok.Data;
import xyz.funnyboy.model.system.SysMenu;

import java.util.List;

/**
 * 路由配置信息
 */
@Data
public class RouterVO
{
    /**
     * 路由名字
     */
    //private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现
     */
    private boolean hidden;

    /**
     * 组件地址
     */
    private String component;

    /**
     * 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
     */
    private Boolean alwaysShow;

    /**
     * 其他元素
     */
    private MetaVO meta;

    /**
     * 子路由
     */
    private List<RouterVO> children;

    /**
     * 构建RouterVO
     *
     * @param menu
     * @param hidden
     * @return {@link RouterVO}
     */
    public static RouterVO build(SysMenu menu, boolean hidden) {
        RouterVO router = new RouterVO();
        router.setHidden(hidden);
        router.setAlwaysShow(false);
        router.setPath(getRouterPath(menu));
        router.setComponent(menu.getComponent());
        router.setMeta(new MetaVO(menu.getName(), menu.getIcon()));
        return router;
    }

    /**
     * 获取路由地址
     *
     * @param menu
     * @return {@link String}
     */
    private static String getRouterPath(SysMenu menu) {
        String routerPath;
        if (menu.getParentId()
                .intValue() != 0) {
            routerPath = menu.getPath();
        }
        else {
            routerPath = "/" + menu.getPath();
        }
        return routerPath;
    }
}
