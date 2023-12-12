package xyz.funnyboy.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.funnyboy.auth.mapper.SysRoleMapper;
import xyz.funnyboy.auth.service.SysRoleService;
import xyz.funnyboy.auth.service.SysUserRoleService;
import xyz.funnyboy.model.system.SysRole;
import xyz.funnyboy.model.system.SysUserRole;
import xyz.funnyboy.vo.system.AssignRoleVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService
{
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 根据用户获取角色数据
     *
     * @param userId
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> findRoleByUserId(Long userId) {
        // 所有角色
        final List<SysRole> allRoleList = this.list();

        // 拥有的角色ID
        final LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId)
                                                                                             .select(SysUserRole::getRoleId);
        final List<Long> roleIdList = sysUserRoleService.list(wrapper)
                                                        .stream()
                                                        .map(SysUserRole::getRoleId)
                                                        .collect(Collectors.toList());

        // 已分配角色
        final List<SysRole> assignRoleList = allRoleList.stream()
                                                        .filter(r -> roleIdList.contains(r.getId()))
                                                        .collect(Collectors.toList());

        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("allRoleList", allRoleList);
        roleMap.put("assignRoleList", assignRoleList);
        return roleMap;
    }

    /**
     * 分配角色
     *
     * @param assignRoleVO
     */
    @Transactional
    @Override
    public void doAssign(AssignRoleVO assignRoleVO) {
        final Long userId = assignRoleVO.getUserId();
        final List<Long> roleIdList = assignRoleVO.getRoleIdList();
        final Function<Long, SysUserRole> function = roleId -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);
            return sysUserRole;
        };
        List<SysUserRole> sysUserRoleList = roleIdList.stream()
                                                      .map(function)
                                                      .collect(Collectors.toList());
        sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
        sysUserRoleService.saveBatch(sysUserRoleList);
    }
}
