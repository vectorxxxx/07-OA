package xyz.funnyboy.process.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import xyz.funnyboy.model.process.ProcessType;

@Repository
@Mapper
public interface ProcessTypeMapper extends BaseMapper<ProcessType>
{
}
