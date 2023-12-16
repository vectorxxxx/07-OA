package xyz.funnyboy.process.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.model.process.ProcessType;

import java.util.List;

public interface ProcessTypeService extends IService<ProcessType>
{
    /**
     * 获取审批分类与对应的审批模板
     *
     * @return {@link List}<{@link ProcessType}>
     */
    List<ProcessType> findProcessType();
}
