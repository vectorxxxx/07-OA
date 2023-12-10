package xyz.funnyboy.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.auth.service.SysRoleService;
import xyz.funnyboy.common.handler.VectorXException;
import xyz.funnyboy.common.result.Result;
import xyz.funnyboy.model.system.SysRole;
import xyz.funnyboy.model.system.SysRoleQueryVO;

import java.util.List;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController
{
    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation(value = "获取全部角色列表")
    @GetMapping("findAll")
    public Result<List<SysRole>> getAll() {
        try {
            int a = 10 / 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new VectorXException(20001, "出现自定义异常");
        }
        final List<SysRole> sysRoleList = sysRoleService.list();
        return Result.ok(sysRoleList);
    }

    @ApiOperation(value = "条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result<Page<SysRole>> pageQueryRole(
            @PathVariable("page")
                    int page,
            @PathVariable("limit")
                    int limit, SysRoleQueryVO sysRoleQueryVO) {
        Page<SysRole> pageParam = new Page<>(page, limit);

        final LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        final String roleName = sysRoleQueryVO.getRoleName();
        if (!StringUtils.isEmpty(roleName)) {
            wrapper.like(SysRole::getRoleName, roleName);
        }

        final Page<SysRole> pageModel = sysRoleService.page(pageParam, wrapper);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取角色")
    @GetMapping("get/{id}")
    public Result<SysRole> get(
            @PathVariable
                    Long id) {
        SysRole role = sysRoleService.getById(id);
        return Result.ok(role);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("save")
    public Result<String> save(
            @RequestBody
            @Validated
                    SysRole role) {
        sysRoleService.save(role);
        return Result.ok();
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("update")
    public Result<String> updateById(
            @RequestBody
                    SysRole role) {
        sysRoleService.updateById(role);
        return Result.ok();
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("remove/{id}")
    public Result<String> remove(
            @PathVariable
                    Long id) {
        sysRoleService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result<String> batchRemove(
            @RequestBody
                    List<Long> idList) {
        sysRoleService.removeByIds(idList);
        return Result.ok();
    }
}
