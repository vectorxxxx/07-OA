package xyz.funnyboy.process.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.model.process.ProcessTemplate;

public interface ProcessTemplateService extends IService<ProcessTemplate>
{
    /**
     * 分页查询
     *
     * @param pageParam
     * @return {@link IPage}<{@link ProcessTemplate}>
     */
    IPage<ProcessTemplate> selectPage(Page<ProcessTemplate> pageParam);

    /**
     * 发布
     *
     * @param id id
     */
    void publish(Long id);
}
