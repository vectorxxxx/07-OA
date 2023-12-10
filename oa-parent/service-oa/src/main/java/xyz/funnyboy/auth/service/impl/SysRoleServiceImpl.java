package xyz.funnyboy.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.auth.mapper.SysRoleMapper;
import xyz.funnyboy.auth.service.SysRoleService;
import xyz.funnyboy.model.system.SysRole;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService
{
}
