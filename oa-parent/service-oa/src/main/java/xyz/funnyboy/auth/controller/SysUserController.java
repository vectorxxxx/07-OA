package xyz.funnyboy.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.auth.service.SysUserService;
import xyz.funnyboy.common.result.Result;
import xyz.funnyboy.model.system.SysUser;
import xyz.funnyboy.vo.system.SysUserQueryVO;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController
{
    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("用户条件分页查询")
    @GetMapping("/{page}/{limit}")
    public Result<Page<SysUser>> list(
            @PathVariable("page")
                    Long page,
            @PathVariable("limit")
                    Long limit, SysUserQueryVO sysUserQueryVO) {
        // 1、获取条件值
        final String keyword = sysUserQueryVO.getKeyword();
        final String createTimeBegin = sysUserQueryVO.getCreateTimeBegin();
        final String createTimeEnd = sysUserQueryVO.getCreateTimeEnd();

        // 2、查询条件
        final LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        // 关键字
        if (!StringUtils.isEmpty(keyword)) {
            wrapper.like(SysUser::getUsername, keyword)
                   .or()
                   .like(SysUser::getName, keyword)
                   .or()
                   .like(SysUser::getPhone, keyword);
        }
        // 起始时间
        if (!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge(SysUser::getCreateTime, createTimeBegin);
        }
        // 截止时间
        if (!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.le(SysUser::getCreateTime, createTimeEnd);
        }

        // 3、分页对象
        Page<SysUser> pageList = new Page<>(page, limit);
        sysUserService.page(pageList, wrapper);

        // 4、封装数据
        return Result.ok(pageList);
    }

    @ApiOperation("获取用户")
    @GetMapping("/get/{id}")
    public Result<SysUser> get(
            @PathVariable
                    Long id) {
        return Result.ok(sysUserService.getById(id));
    }

    @ApiOperation("新增用户")
    @PostMapping("/save")
    public Result<Object> save(
            @RequestBody
                    SysUser sysUser) {
        try {
            final boolean save = sysUserService.save(sysUser);
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

    @ApiOperation("修改用户")
    @PutMapping("/update")
    public Result<Object> update(
            @RequestBody
                    SysUser sysUser) {
        try {
            final boolean update = sysUserService.updateById(sysUser);
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

    @ApiOperation("删除用户")
    @DeleteMapping("/remove/{id}")
    public Result<Object> remove(
            @PathVariable
                    Long id) {
        try {
            final boolean remove = sysUserService.removeById(id);
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
}
