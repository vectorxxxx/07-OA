package xyz.funnyboy.process.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.funnyboy.model.process.ProcessTemplate;
import xyz.funnyboy.model.process.ProcessType;
import xyz.funnyboy.process.mapper.ProcessTypeMapper;
import xyz.funnyboy.process.service.ProcessTemplateService;
import xyz.funnyboy.process.service.ProcessTypeService;

import java.util.List;

@Service
public class ProcessTypeServiceImpl extends ServiceImpl<ProcessTypeMapper, ProcessType> implements ProcessTypeService
{
    @Autowired
    private ProcessTemplateService processTemplateService;

    /**
     * 获取审批分类与对应的审批模板
     *
     * @return {@link List}<{@link ProcessType}>
     */
    @Override
    public List<ProcessType> findProcessType() {
        // 1 查询所有审批分类，返回list集合
        final List<ProcessType> processTypeList = baseMapper.selectList(null);

        // 2 遍历返回所有审批分类list集合
        processTypeList.forEach(processType -> {
            // 3 根据审批分类id查询对应的审批模板
            final List<ProcessTemplate> processTemplateList = processTemplateService.list(
                    new LambdaQueryWrapper<ProcessTemplate>().eq(ProcessTemplate::getProcessTypeId, processType.getId()));

            // 4 将审批模板list集合添加到审批分类对象中
            processType.setProcessTemplateList(processTemplateList);
        });
        return processTypeList;
    }
}
