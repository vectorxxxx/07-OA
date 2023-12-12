package xyz.funnyboy.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.auth.service.SysMenuService;
import xyz.funnyboy.common.result.Result;
import xyz.funnyboy.model.system.SysMenu;
import xyz.funnyboy.vo.system.AssignMenuVO;

import java.util.List;

@Api(tags = "菜单管理")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController
{
    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation(value = "获取菜单")
    @GetMapping("findNodes")
    public Result<List<SysMenu>> findNodes() {
        List<SysMenu> list = sysMenuService.findNodes();
        return Result.ok(list);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public Result<Object> save(
            @RequestBody
                    SysMenu permission) {
        try {
            final boolean save = sysMenuService.save(permission);
            return save ?
                    Result.ok() :
                    Result.fail()
                          .message("新增失败");
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public Result<Object> updateById(
            @RequestBody
                    SysMenu permission) {
        try {
            final boolean update = sysMenuService.updateById(permission);
            return update ?
                    Result.ok() :
                    Result.fail()
                          .message("修改失败");
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("remove/{id}")
    public Result<Object> remove(
            @PathVariable
                    Long id) {
        try {
            final boolean remove = sysMenuService.removeById(id);
            return remove ?
                    Result.ok() :
                    Result.fail()
                          .message("删除失败");
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public Result<List<SysMenu>> toAssign(
            @PathVariable
                    Long roleId) {
        List<SysMenu> list = sysMenuService.findSysMenuByRoleId(roleId);
        return Result.ok(list);
    }

    @ApiOperation(value = "给角色分配菜单")
    @PostMapping("doAssign")
    public Result<Object> doAssign(
            @RequestBody
                    AssignMenuVO assignMenuVO) {
        try {
            sysMenuService.doAssign(assignMenuVO);
            return Result.ok();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.fail()
                         .message(e.getMessage());
        }
    }
}
