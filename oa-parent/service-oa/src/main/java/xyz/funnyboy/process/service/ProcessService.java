package xyz.funnyboy.process.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.model.process.Process;
import xyz.funnyboy.vo.process.ProcessQueryVO;
import xyz.funnyboy.vo.process.ProcessVO;

public interface ProcessService extends IService<Process>
{
    /**
     * 分页查询
     *
     * @param pageParam      页面参数
     * @param processQueryVO 流程查询 vo
     * @return {@link IPage}<{@link ProcessVO}>
     */
    IPage<ProcessVO> selectPage(Page<ProcessVO> pageParam, ProcessQueryVO processQueryVO);

    /**
     * 通过 ZIP 部署
     *
     * @param deployPath 部署路径
     */
    void deployByZip(String deployPath);
}
