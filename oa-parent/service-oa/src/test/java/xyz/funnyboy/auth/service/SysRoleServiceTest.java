package xyz.funnyboy.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.funnyboy.model.system.SysRole;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class SysRoleServiceTest
{
    @Autowired
    private SysRoleService sysRoleService;


    @Test
    public void testList() {
        final List<SysRole> sysRoles = sysRoleService.list();
        sysRoles.forEach(System.out::println);
    }

    @Test
    public void testSave() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("部门管理员");
        sysRole.setRoleCode("dept");
        sysRole.setDescription("部门管理员");

        final boolean save = sysRoleService.save(sysRole);
        System.out.println(sysRole.getId());
        System.out.println(save);
    }

    @Test
    public void testUpdateById() {
        SysRole sysRole = sysRoleService.getById(12);
        sysRole.setRoleName("部门管理员1");
        sysRole.setRoleCode("dept1");
        sysRole.setDescription("部门管理员1");
        final boolean update = sysRoleService.updateById(sysRole);
        System.out.println(update);
    }

    @Test
    public void testRemoveById() {
        final boolean remove = sysRoleService.removeById(13);
        System.out.println(remove);
    }

    @Test
    public void testRemoveByIds() {
        final boolean remove = sysRoleService.removeByIds(Arrays.asList(9, 10));
        System.out.println(remove);
    }

    @Test
    public void testQuery() {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name", "系统管理员");
        final SysRole sysRole = sysRoleService.getOne(wrapper);
        System.out.println(sysRole);
    }

    @Test
    public void testLambdaQuery() {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRoleName, "部门管理员");
        final List<SysRole> selectList = sysRoleService.list(wrapper);
        System.out.println(selectList);
    }
}
