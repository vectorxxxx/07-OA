package xyz.funnyboy.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.funnyboy.model.system.SysMenu;

import java.util.List;

@Repository
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu>
{
    /**
     * 通过用户ID查询菜单
     *
     * @param userId
     * @return {@link List}<{@link SysMenu}>
     */
    List<SysMenu> findMenuListByUserId(
            @Param("userId")
                    Long userId);
}
