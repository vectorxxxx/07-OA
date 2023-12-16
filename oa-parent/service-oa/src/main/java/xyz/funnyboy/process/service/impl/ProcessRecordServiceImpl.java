package xyz.funnyboy.process.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.funnyboy.auth.service.SysUserService;
import xyz.funnyboy.model.process.ProcessRecord;
import xyz.funnyboy.model.system.SysUser;
import xyz.funnyboy.process.mapper.ProcessRecordMapper;
import xyz.funnyboy.process.service.ProcessRecordService;
import xyz.funnyboy.security.custom.LoginUserInfoHelper;

/**
 * @author VectorX
 * @version V1.0
 * @description 流程记录Service
 * @date 16/12/2023
 */
@Service
public class ProcessRecordServiceImpl extends ServiceImpl<ProcessRecordMapper, ProcessRecord> implements ProcessRecordService
{
    @Autowired
    private SysUserService sysUserService;

    /**
     * 记录提交记录
     *
     * @param processId   流程 ID
     * @param status      状态
     * @param description 描述
     */
    @Override
    public void record(Long processId, Integer status, String description) {
        final SysUser sysUser = sysUserService.getById(LoginUserInfoHelper.getUserId());

        ProcessRecord processRecord = new ProcessRecord();
        processRecord.setProcessId(processId);
        processRecord.setStatus(status);
        processRecord.setDescription(description);
        processRecord.setOperateUserId(sysUser.getId());
        processRecord.setOperateUser(sysUser.getName());
        this.save(processRecord);
    }
}
