package xyz.funnyboy.process.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.funnyboy.model.process.Process;
import xyz.funnyboy.vo.process.ProcessQueryVO;
import xyz.funnyboy.vo.process.ProcessVO;

@Repository
@Mapper
public interface ProcessMapper extends BaseMapper<Process>
{
    IPage<ProcessVO> selectPage(Page<ProcessVO> page,
                                @Param("vo")
                                        ProcessQueryVO processQueryVO);
}
