package xyz.funnyboy.wechat.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.funnyboy.model.wechat.Menu;
import xyz.funnyboy.vo.wechat.MenuVO;
import xyz.funnyboy.wechat.mapper.MenuMapper;
import xyz.funnyboy.wechat.service.MenuService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author VectorX
 * @version V1.0
 * @description 菜单管理Service
 * @date 16/12/2023
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService
{

    @Autowired
    private WxMpService wxMpService;

    @Value("${wechat.frontUrl}")
    private String frontUrl;

    /**
     * 查找菜单信息
     *
     * @return {@link List}<{@link MenuVO}>
     */
    @Override
    public List<MenuVO> findMenuInfo() {
        List<MenuVO> menuVOList = new ArrayList<>();
        // 所有菜单
        List<Menu> menuList = baseMapper.selectList(null);
        // 一级菜单
        List<Menu> oneMenuList = menuList.stream()
                                         .filter(menu -> menu.getParentId() == 0)
                                         .collect(Collectors.toList());
        for (Menu oneMenu : oneMenuList) {
            // 二级菜单
            List<Menu> twoMenuList = menuList.stream()
                                             .filter(menu -> menu.getParentId()
                                                                 .longValue() == oneMenu.getId())
                                             .sorted(Comparator.comparing(Menu::getSort))
                                             .collect(Collectors.toList());
            List<MenuVO> children = new ArrayList<>();
            for (Menu twoMenu : twoMenuList) {
                MenuVO twoMenuVo = new MenuVO();
                BeanUtils.copyProperties(twoMenu, twoMenuVo);
                children.add(twoMenuVo);
            }
            // 设置子菜单
            MenuVO oneMenuVo = new MenuVO();
            BeanUtils.copyProperties(oneMenu, oneMenuVo);
            oneMenuVo.setChildren(children);
            menuVOList.add(oneMenuVo);
        }
        return menuVOList;
    }

    /**
     * 推送菜单
     */
    @Override
    public void syncMenu() {
        // 查找菜单信息
        final List<MenuVO> menuVOList = this.findMenuInfo();

        // 菜单
        JSONArray buttonList = new JSONArray();
        for (MenuVO oneMenuVO : menuVOList) {
            JSONObject one = new JSONObject();
            one.put("name", oneMenuVO.getName());

            final List<MenuVO> children = oneMenuVO.getChildren();
            if (CollectionUtils.isEmpty(children)) {
                one.put("type", oneMenuVO.getType());
                one.put("url", frontUrl + "/#" + oneMenuVO.getUrl());
            }
            else {
                JSONArray subButtonList = new JSONArray();
                for (MenuVO twoMenuVO : children) {
                    final String type = twoMenuVO.getType();

                    JSONObject view = new JSONObject();
                    view.put("name", twoMenuVO.getName());
                    view.put("type", type);
                    if (type.equals("view")) {
                        view.put("url", frontUrl + "/#" + twoMenuVO.getUrl());
                    }
                    else {
                        view.put("key", twoMenuVO.getMeunKey());
                    }
                    subButtonList.add(view);
                }
                one.put("sub_button", subButtonList);
            }
            buttonList.add(one);
        }
        JSONObject button = new JSONObject();
        button.put("button", buttonList);

        // 调用接口
        try {
            wxMpService.getMenuService()
                       .menuCreate(button.toString());
        }
        catch (WxErrorException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除菜单
     */
    @SneakyThrows
    @Override
    public void removeMenu() {
        wxMpService.getMenuService()
                   .menuDelete();
    }
}
