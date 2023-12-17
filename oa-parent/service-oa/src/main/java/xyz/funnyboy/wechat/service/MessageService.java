package xyz.funnyboy.wechat.service;

/**
 * @author VectorX
 * @version V1.0
 * @description 消息推送
 * @date 17/12/2023
 */
public interface MessageService
{
    /**
     * 推送待审批人员
     *
     * @param processId
     * @param userId
     * @param taskId
     */
    void pushPendingMessage(Long processId, Long userId, String taskId);

    /**
     * 审批后推送提交审批人员
     *
     * @param processId
     * @param userId
     * @param status
     */
    void pushProcessedMessage(Long processId, Long userId, Integer status);
}
