package xyz.funnyboy.wechat.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.common.result.Result;
import xyz.funnyboy.model.wechat.Menu;
import xyz.funnyboy.vo.wechat.MenuVO;
import xyz.funnyboy.wechat.service.MenuService;

import java.util.List;

/**
 * @author VectorX
 * @version V1.0
 * @description 菜单管理Controller
 * @date 17/12/2023
 */
@RestController
@RequestMapping("/admin/wechat/menu")
@Slf4j
public class MenuController
{
    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    @PreAuthorize("hasAuthority('bnt.menu.list')")
    public Result<Object> get(
            @PathVariable
                    Long id) {
        try {
            final Menu menu = menuService.getById(id);
            return Result.ok(menu);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "新增")
    @PostMapping("save")
    @PreAuthorize("hasAuthority('bnt.menu.add')")
    public Result<Object> save(
            @RequestBody
                    Menu menu) {
        try {
            final boolean save = menuService.save(menu);
            return save ?
                    Result.ok() :
                    Result.fail()
                          .message("保存失败");
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    @PreAuthorize("hasAuthority('bnt.menu.update')")
    public Result<Object> updateById(
            @RequestBody
                    Menu menu) {
        try {
            final boolean update = menuService.updateById(menu);
            return update ?
                    Result.ok() :
                    Result.fail()
                          .message("修改失败");
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    @PreAuthorize("hasAuthority('bnt.menu.remove')")
    public Result<Object> remove(
            @PathVariable
                    Long id) {
        try {
            final boolean remove = menuService.removeById(id);
            return remove ?
                    Result.ok() :
                    Result.fail()
                          .message("删除失败");
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "获取全部菜单")
    @GetMapping("findMenuInfo")
    @PreAuthorize("hasAuthority('bnt.menu.list')")
    public Result<Object> findMenuInfo() {
        try {
            final List<MenuVO> menuInfo = menuService.findMenuInfo();
            return Result.ok(menuInfo);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "同步菜单")
    @GetMapping("syncMenu")
    @PreAuthorize("hasAuthority('bnt.menu.syncMenu')")
    public Result<Object> createMenu() {
        try {
            menuService.syncMenu();
            return Result.ok();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("removeMenu")
    @PreAuthorize("hasAuthority('bnt.menu.removeMenu')")
    public Result<Object> removeMenu() {
        try {
            menuService.removeMenu();
            return Result.ok();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }
}
