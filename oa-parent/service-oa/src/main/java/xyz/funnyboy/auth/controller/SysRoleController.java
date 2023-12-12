package xyz.funnyboy.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.auth.service.SysRoleService;
import xyz.funnyboy.common.result.Result;
import xyz.funnyboy.model.system.SysRole;
import xyz.funnyboy.vo.system.AssignRoleVO;
import xyz.funnyboy.vo.system.SysRoleQueryVO;

import java.util.List;
import java.util.Map;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SysRoleController.class);

    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation(value = "获取全部角色列表")
    @GetMapping("findAll")
    public Result<List<SysRole>> getAll() {
        // try {
        //     int a = 10 / 0;
        // }
        // catch (ArithmeticException e) {
        //     LOGGER.error(e.getMessage(), e);
        //     throw new VectorXException(20001, "出现自定义异常");
        // }
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
        Page<SysRole> pageModel = new Page<>(page, limit);

        final LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        final String roleName = sysRoleQueryVO.getRoleName();
        if (!StringUtils.isEmpty(roleName)) {
            wrapper.like(SysRole::getRoleName, roleName);
        }

        sysRoleService.page(pageModel, wrapper);
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
        final boolean save = sysRoleService.save(role);
        if (save) {
            return Result.ok();
        }
        else {
            return Result.fail();
        }
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
        final boolean remove = sysRoleService.removeById(id);
        if (remove) {
            return Result.ok();
        }
        else {
            return Result.fail();
        }
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result<String> batchRemove(
            @RequestBody
                    List<Long> idList) {
        final boolean remove = sysRoleService.removeByIds(idList);
        if (remove) {
            return Result.ok();
        }
        else {
            return Result.fail();
        }
    }

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public Result<Map<String, Object>> toAssign(
            @PathVariable("userId")
                    Long userId) {
        Map<String, Object> roleMap = sysRoleService.findRoleByUserId(userId);
        return Result.ok(roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public Result<Object> doAssign(
            @RequestBody
                    AssignRoleVO assignRoleVO) {
        try {
            sysRoleService.doAssign(assignRoleVO);
            return Result.ok();
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }
}
