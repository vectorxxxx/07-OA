package xyz.funnyboy.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import xyz.funnyboy.model.system.SysUser;

@Repository
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser>
{
}
