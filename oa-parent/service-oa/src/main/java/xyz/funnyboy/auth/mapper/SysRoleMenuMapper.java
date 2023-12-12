package xyz.funnyboy.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import xyz.funnyboy.model.system.SysRoleMenu;

@Repository
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu>
{
}
