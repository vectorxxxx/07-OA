package xyz.funnyboy.process.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.model.process.ProcessType;
import xyz.funnyboy.process.mapper.ProcessTypeMapper;
import xyz.funnyboy.process.service.ProcessTypeService;

@Service
public class ProcessTypeServiceImpl extends ServiceImpl<ProcessTypeMapper, ProcessType> implements ProcessTypeService
{
}
