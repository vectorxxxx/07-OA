package xyz.funnyboy.process.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import xyz.funnyboy.model.process.ProcessTemplate;
import xyz.funnyboy.model.process.ProcessType;
import xyz.funnyboy.process.mapper.ProcessTemplateMapper;
import xyz.funnyboy.process.service.ProcessService;
import xyz.funnyboy.process.service.ProcessTemplateService;
import xyz.funnyboy.process.service.ProcessTypeService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProcessTemplateServiceImpl extends ServiceImpl<ProcessTemplateMapper, ProcessTemplate> implements ProcessTemplateService
{
    @Resource
    private ProcessTypeService processTypeService;

    @Resource
    private ProcessService processService;

    /**
     * 分页查询
     *
     * @param pageParam
     * @return {@link IPage}<{@link ProcessTemplate}>
     */
    @Override
    public IPage<ProcessTemplate> selectPage(Page<ProcessTemplate> pageParam) {
        // 查询审批模板
        final IPage<ProcessTemplate> page = this.getBaseMapper()
                                                .selectPage(pageParam, new LambdaQueryWrapper<ProcessTemplate>().orderByDesc(ProcessTemplate::getId));
        final List<ProcessTemplate> processTemplateList = page.getRecords();

        // 过滤审批类型ID
        final List<Long> processTypeIdList = processTemplateList.stream()
                                                                .map(ProcessTemplate::getProcessTypeId)
                                                                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(processTypeIdList)) {
            return page;
        }

        // 获取流程类型列表
        final Map<Long, ProcessType> processTypeMap = processTypeService.list(new LambdaQueryWrapper<ProcessType>().in(ProcessType::getId, processTypeIdList))
                                                                        .stream()
                                                                        .collect(Collectors.toMap(ProcessType::getId, processType -> processType));

        // 遍历流程模板列表，设置流程类型名称
        processTemplateList.forEach(processTemplate -> {
            final ProcessType processType = processTypeMap.get(processTemplate.getProcessTypeId());
            if (processType != null) {
                processTemplate.setProcessTypeName(processType.getName());
            }
        });

        return page;
    }

    /**
     * 发布
     *
     * @param id id
     */
    @Override
    public void publish(Long id) {
        final ProcessTemplate processTemplate = this.getById(id);
        final String processDefinitionPath = processTemplate.getProcessDefinitionPath();
        if (!StringUtils.isEmpty(processDefinitionPath)) {
            // 发布在线流程设计
            processService.deployByZip(processDefinitionPath);
            // 置状态
            processTemplate.setStatus(1);
            this.updateById(processTemplate);
        }
    }
}
