package xyz.funnyboy.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.auth.mapper.SysUserMapper;
import xyz.funnyboy.auth.service.SysUserService;
import xyz.funnyboy.model.system.SysUser;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService
{
}
