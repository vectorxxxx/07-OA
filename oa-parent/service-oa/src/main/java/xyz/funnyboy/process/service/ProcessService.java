package xyz.funnyboy.process.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.model.process.Process;
import xyz.funnyboy.vo.process.ApprovalVO;
import xyz.funnyboy.vo.process.ProcessFormVO;
import xyz.funnyboy.vo.process.ProcessQueryVO;
import xyz.funnyboy.vo.process.ProcessVO;

import java.util.Map;

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

    /**
     * 启动流程实例
     *
     * @param processFormVO 流程表单 VO
     */
    void startUp(ProcessFormVO processFormVO);

    /**
     * 分页查询待处理任务
     *
     * @param pageParam 页面参数
     * @return {@link IPage}<{@link ProcessVO}>
     */
    IPage<ProcessVO> findPending(Page<ProcessVO> pageParam);

    /**
     * 显示审批详情
     *
     * @param id
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> show(Long id);

    /**
     * 审批
     *
     * @param approvalVO 批准 VO
     */
    void approve(ApprovalVO approvalVO);

    /**
     * 查询已处理活动
     *
     * @param pageParam 页面参数
     * @return {@link IPage}<{@link ProcessVO}>
     */
    IPage<ProcessVO> findProcessed(IPage<Process> pageParam);

    /**
     * 查询已发起活动
     *
     * @param pageParam 页面参数
     * @return {@link IPage}<{@link ProcessVO}>
     */
    IPage<ProcessVO> findStarted(Page<ProcessVO> pageParam);
}
