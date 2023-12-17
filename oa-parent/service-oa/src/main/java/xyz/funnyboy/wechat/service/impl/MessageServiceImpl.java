package xyz.funnyboy.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.funnyboy.auth.service.SysUserService;
import xyz.funnyboy.model.process.Process;
import xyz.funnyboy.model.process.ProcessTemplate;
import xyz.funnyboy.model.system.SysUser;
import xyz.funnyboy.process.service.ProcessService;
import xyz.funnyboy.process.service.ProcessTemplateService;
import xyz.funnyboy.security.custom.LoginUserInfoHelper;
import xyz.funnyboy.wechat.service.MessageService;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author VectorX
 * @version V1.0
 * @description 消息推送
 * @date 17/12/2023
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService
{
    /**
     * 微信接口
     */
    @Resource
    private WxMpService wxMpService;

    /**
     * 审批流程接口
     */
    @Resource
    private ProcessService processService;

    /**
     * 审批模板接口
     */
    @Resource
    private ProcessTemplateService processTemplateService;

    /**
     * 用户接口
     */
    @Resource
    private SysUserService sysUserService;

    @Value("${wechat.frontUrl}")
    private String frontUrl;

    /**
     * 推送待审批人员
     *
     * @param processId
     * @param userId
     * @param taskId
     */
    @SneakyThrows
    @Override
    public void pushPendingMessage(Long processId, Long userId, String taskId) {
        // 审批流程
        final Process process = processService.getById(processId);
        // 审批模板
        final ProcessTemplate processTemplate = processTemplateService.getById(process.getProcessTemplateId());
        // 用户
        final SysUser sysUser = sysUserService.getById(userId);
        final SysUser submitSysUser = sysUserService.getById(process.getUserId());

        // 推送待审批人员
        String openId = sysUser.getOpenId();
        // 方便测试，给默认值（开发者本人的openId）
        if (StringUtils.isEmpty(openId)) {
            openId = "oWL__6qCykk30OZly25Mn8k46CEI";
        }
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                                                                 //要推送的用户openid
                                                                 .toUser(openId)
                                                                 //模板id
                                                                 .templateId("z8-W1R2oMzz0hxMwNSNFKKZt9bn7qWmnqitkpvH0oHA")
                                                                 //点击模板消息要访问的网址
                                                                 .url(frontUrl + "/#/show/" + processId + "/" + taskId)
                                                                 .build();
        JSONObject jsonObject = JSON.parseObject(process.getFormValues());
        JSONObject formShowData = jsonObject.getJSONObject("formShowData");
        StringBuilder content = new StringBuilder();
        for (Map.Entry<String, Object> entry : formShowData.entrySet()) {
            content.append("\n ")
                   .append(entry.getKey())
                   .append("：")
                   .append(entry.getValue());
        }
        log.info("content=" + content);
        templateMessage.addData(new WxMpTemplateData("first", submitSysUser.getName() + "提交了" + processTemplate.getName() + "审批申请，请注意查看。", "#272727"));
        templateMessage.addData(new WxMpTemplateData("keyword1", process.getProcessCode(), "#272727"));
        templateMessage.addData(new WxMpTemplateData("keyword2", new DateTime(process.getCreateTime()).toString("yyyy-MM-dd HH:mm:ss"), "#272727"));
        templateMessage.addData(new WxMpTemplateData("content", content.toString(), "#272727"));
        String msg = wxMpService.getTemplateMsgService()
                                .sendTemplateMsg(templateMessage);
        log.info("推送消息返回：{}", msg);
    }

    /**
     * 审批后推送提交审批人员
     *
     * @param processId
     * @param userId
     * @param status
     */
    @SneakyThrows
    @Override
    public void pushProcessedMessage(Long processId, Long userId, Integer status) {
        // 审批流程
        final Process process = processService.getById(processId);
        // 审批模板
        final ProcessTemplate processTemplate = processTemplateService.getById(process.getProcessTemplateId());
        // 用户
        final SysUser sysUser = sysUserService.getById(userId);
        final SysUser currentSysUser = sysUserService.getById(LoginUserInfoHelper.getUserId());
        String openid = sysUser.getOpenId();

        if (StringUtils.isEmpty(openid)) {
            openid = "oWL__6qCykk30OZly25Mn8k46CEI";
        }
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                                                                 .toUser(openid)//要推送的用户openid
                                                                 .templateId("AxK0RcboFq1Zm2oSkhHUq27yA5GR82a143t6T0BAkek")//模板id
                                                                 .url(frontUrl + "/#/show/" + processId + "/0")//点击模板消息要访问的网址
                                                                 .build();
        JSONObject jsonObject = JSON.parseObject(process.getFormValues());
        JSONObject formShowData = jsonObject.getJSONObject("formShowData");
        StringBuilder content = new StringBuilder();
        for (Map.Entry<String, Object> entry : formShowData.entrySet()) {
            content.append("\n ")
                   .append(entry.getKey())
                   .append("：")
                   .append(entry.getValue());
        }
        log.info("content=" + content);
        templateMessage.addData(new WxMpTemplateData("first", "你发起的" + processTemplate.getName() + "审批申请已经被处理了，请注意查看。", "#272727"));
        templateMessage.addData(new WxMpTemplateData("keyword1", process.getProcessCode(), "#272727"));
        templateMessage.addData(new WxMpTemplateData("keyword2", new DateTime(process.getCreateTime()).toString("yyyy-MM-dd HH:mm:ss"), "#272727"));
        templateMessage.addData(new WxMpTemplateData("keyword3", currentSysUser.getName(), "#272727"));
        templateMessage.addData(new WxMpTemplateData("keyword4", status == 1 ?
                "审批通过" :
                "审批拒绝", status == 1 ?
                "#009966" :
                "#FF0033"));
        templateMessage.addData(new WxMpTemplateData("content", content.toString(), "#272727"));
        String msg = wxMpService.getTemplateMsgService()
                                .sendTemplateMsg(templateMessage);
        log.info("推送消息返回：{}", msg);
    }
}
