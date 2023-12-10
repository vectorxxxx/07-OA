package xyz.funnyboy.auth.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.funnyboy.model.system.SysRole;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class SysRoleMapperTest
{
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Test
    public void testSelectList() {
        final List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        for (SysRole sysRole : sysRoles) {
            System.out.println(sysRole);
        }
    }

    @Test
    public void testInsert() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("部门管理员");
        sysRole.setRoleCode("dept");
        sysRole.setDescription("部门管理员");

        final int insert = sysRoleMapper.insert(sysRole);
        System.out.println(sysRole.getId());
        System.out.println(insert);
    }

    @Test
    public void testUpdate() {
        SysRole sysRole = sysRoleMapper.selectById(9);
        sysRole.setRoleName("系统管理员11");
        sysRole.setRoleCode("系统管理员11");
        final int update = sysRoleMapper.updateById(sysRole);
        System.out.println(update);
    }

    @Test
    public void testDelete() {
        final int delete = sysRoleMapper.deleteById(9);
        System.out.println(delete);
    }

    @Test
    public void testDeleteBatch() {
        final int deleteBatch = sysRoleMapper.deleteBatchIds(Arrays.asList(9, 10));
        System.out.println(deleteBatch);
    }

    @Test
    public void testQuery() {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name", "系统管理员");
        final SysRole sysRole = sysRoleMapper.selectOne(wrapper);
        System.out.println(sysRole);
    }

    @Test
    public void testLambdaQuery() {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRoleName, "部门管理员");
        final List<SysRole> selectList = sysRoleMapper.selectList(wrapper);
        System.out.println(selectList);
    }
}
