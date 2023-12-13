package xyz.funnyboy.auth.util;

import xyz.funnyboy.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MenuHelper
{
    //使用递归方法建菜单
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        return sysMenuList.stream()
                          .filter(menu -> menu.getParentId() == 0)
                          .map(menu -> getChildren(menu, sysMenuList))
                          .collect(Collectors.toList());
    }

    private static SysMenu getChildren(SysMenu sysMenu, List<SysMenu> sysMenuList) {
        sysMenu.setChildren(new ArrayList<>());
        sysMenuList.stream()
                   .filter(menu -> menu.getParentId()
                                       .longValue() == sysMenu.getId()
                                                              .longValue())
                   .forEach(menu -> sysMenu.getChildren()
                                           .add(getChildren(menu, sysMenuList)));
        return sysMenu;
    }
}
