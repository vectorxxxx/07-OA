package xyz.funnyboy.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.funnyboy.auth.service.SysRoleService;
import xyz.funnyboy.common.result.Result;
import xyz.funnyboy.model.system.SysRole;

import java.util.List;

@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController
{
    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("findAll")
    public Result<List<SysRole>> getAll() {
        final List<SysRole> sysRoleList = sysRoleService.list();
        return Result.ok(sysRoleList);
    }
}
