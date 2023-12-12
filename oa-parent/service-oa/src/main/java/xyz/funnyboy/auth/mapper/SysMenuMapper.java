package xyz.funnyboy.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import xyz.funnyboy.model.system.SysMenu;

@Repository
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu>
{
}
