package xyz.funnyboy.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.auth.mapper.SysRoleMenuMapper;
import xyz.funnyboy.auth.service.SysRoleMenuService;
import xyz.funnyboy.model.system.SysRoleMenu;

@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService
{
}
