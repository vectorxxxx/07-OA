package xyz.funnyboy.process.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.model.process.ProcessRecord;

public interface ProcessRecordService extends IService<ProcessRecord>
{
    /**
     * 记录提交记录
     *
     * @param processId   流程 ID
     * @param status      状态
     * @param description 描述
     */
    void record(Long processId, Integer status, String description);
}
