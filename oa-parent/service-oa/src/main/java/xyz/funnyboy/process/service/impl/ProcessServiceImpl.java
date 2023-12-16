package xyz.funnyboy.process.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.funnyboy.auth.service.SysUserService;
import xyz.funnyboy.model.process.Process;
import xyz.funnyboy.model.process.ProcessRecord;
import xyz.funnyboy.model.process.ProcessTemplate;
import xyz.funnyboy.model.system.SysUser;
import xyz.funnyboy.process.mapper.ProcessMapper;
import xyz.funnyboy.process.service.ProcessRecordService;
import xyz.funnyboy.process.service.ProcessService;
import xyz.funnyboy.process.service.ProcessTemplateService;
import xyz.funnyboy.security.custom.LoginUserInfoHelper;
import xyz.funnyboy.vo.process.ApprovalVO;
import xyz.funnyboy.vo.process.ProcessFormVO;
import xyz.funnyboy.vo.process.ProcessQueryVO;
import xyz.funnyboy.vo.process.ProcessVO;

import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipInputStream;

@Slf4j
@Service
public class ProcessServiceImpl extends ServiceImpl<ProcessMapper, Process> implements ProcessService
{
    /**
     * 用户信息Service
     */
    @Autowired
    private SysUserService sysUserService;

    /**
     * 审批模板Service
     */
    @Autowired
    private ProcessTemplateService processTemplateService;

    @Autowired
    private ProcessRecordService processRecordService;

    /**
     * 流程相关Service
     */
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Override
    public IPage<ProcessVO> selectPage(Page<ProcessVO> pageParam, ProcessQueryVO processQueryVO) {
        return baseMapper.selectPage(pageParam, processQueryVO);
    }

    /**
     * 通过 ZIP 部署
     *
     * @param deployPath 部署路径
     */
    @Override
    public void deployByZip(String deployPath) {
        // 定义zip输入流
        final InputStream inputStream = this.getClass()
                                            .getClassLoader()
                                            .getResourceAsStream(deployPath);
        final ZipInputStream zipInputStream = new ZipInputStream(Objects.requireNonNull(inputStream));

        // 流程部署
        final Deployment deployment = repositoryService.createDeployment()
                                                       .addZipInputStream(zipInputStream)
                                                       .deploy();
        log.info("部署ID：" + deployment.getId());
        log.info("部署名称：" + deployment.getName());
    }

    /**
     * 启动流程实例
     *
     * @param processFormVO 流程表单 VO
     */
    @Override
    public void startUp(ProcessFormVO processFormVO) {
        // 1、查询用户信息
        // userId从ThreadLocal中获取（TokenAuthenticationFilter#getAuthentication中已设置）
        final Long userId = LoginUserInfoHelper.getUserId();
        final SysUser sysUser = sysUserService.getById(userId);
        final String username = sysUser.getUsername();

        // 2、获取审批模板信息
        final ProcessTemplate processTemplate = processTemplateService.getById(processFormVO.getProcessTemplateId());
        final String processTemplateName = processTemplate.getName();
        final String processDefinitionKey = processTemplate.getProcessDefinitionKey();

        // 3、设置审批流程信息
        Process process = new Process();
        BeanUtils.copyProperties(processFormVO, process);
        process.setProcessCode(System.currentTimeMillis() + "");
        process.setUserId(userId);
        process.setFormValues(processFormVO.getFormValues());
        process.setTitle(username + "发起" + processTemplateName + "申请");
        // 状态（0：默认 1：审批中 2：审批通过 -1：驳回）
        process.setStatus(1);
        baseMapper.insert(process);

        // 4、启动流程实例
        final String businessKey = String.valueOf(process.getId());
        // 流程参数
        Map<String, Object> variables = new HashMap<>();
        final JSONObject jsonObject = JSON.parseObject(process.getFormValues());
        final JSONObject formData = jsonObject.getJSONObject("formData");
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, Object> entry : formData.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        variables.put("data", map);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);

        // 5、业务表关联当前流程实例ID
        final String processInstanceId = processInstance.getId();
        process.setProcessInstanceId(processInstanceId);

        // 6、计算下一个审批人，可能有多个（并行审批）
        final List<Task> taskList = this.getCurrentTaskList(processInstanceId);
        if (!CollectionUtils.isEmpty(taskList)) {
            // 获取审批人列表
            final String[] assigneeArr = taskList.stream()
                                                 .map(task -> {
                                                     final String assignee = task.getAssignee();
                                                     final SysUser user = sysUserService.getByUsername(assignee);
                                                     if (user == null) {
                                                         return assignee;
                                                     }
                                                     return user.getName();
                                                 })
                                                 .toArray(String[]::new);
            process.setDescription("等待" + StringUtils.join(assigneeArr, "、") + "审批");
        }
        this.updateById(process);

        // 7、记录操作行为
        processRecordService.record(process.getId(), 1, "发起申请");
    }

    /**
     * 分页查询待处理任务
     *
     * @param pageParam 页面参数
     * @return {@link IPage}<{@link ProcessVO}>
     */
    @Override
    public IPage<ProcessVO> findPending(Page<ProcessVO> pageParam) {
        // 根据当前人的ID查询
        final String username = LoginUserInfoHelper.getUsername();
        final long current = pageParam.getCurrent();
        final long size = pageParam.getSize();
        final TaskQuery query = taskService.createTaskQuery()
                                           .taskAssignee(username)
                                           .orderByTaskCreateTime()
                                           .desc();
        final List<Task> taskList = query.listPage((int) ((current - 1) * size), (int) size);
        final long totalCount = query.count();

        // 根据流程的业务ID查询实体并关联
        final ArrayList<ProcessVO> processVOList = new ArrayList<>();
        for (Task task : taskList) {
            final String processInstanceId = task.getProcessInstanceId();
            final ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                                                                  .processInstanceId(processInstanceId)
                                                                  .singleResult();
            if (processInstance == null) {
                continue;
            }
            final String businessKey = processInstance.getBusinessKey();
            if (StringUtils.isEmpty(businessKey)) {
                continue;
            }

            Process process = this.getById(Long.parseLong(businessKey));
            ProcessVO processVO = new ProcessVO();
            if (process == null) {
                continue;
            }
            BeanUtils.copyProperties(process, processVO);
            processVO.setTaskId(task.getId());
            processVOList.add(processVO);
        }

        // 返回分页信息
        final IPage<ProcessVO> page = new Page<>(pageParam.getCurrent(), pageParam.getSize(), totalCount);
        page.setRecords(processVOList);
        return page;
    }

    /**
     * 显示审批详情
     *
     * @param id
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> show(Long id) {
        // 1、查询审批申请
        final Process process = this.getById(id);

        // 2、查询审批记录
        final List<ProcessRecord> processRecordList = processRecordService.list(new LambdaQueryWrapper<ProcessRecord>().eq(ProcessRecord::getProcessId, id));

        // 3、查询审批模板
        final ProcessTemplate processTemplate = processTemplateService.getById(process.getProcessTemplateId());

        // 4、查询任务列表
        final List<Task> taskList = this.getCurrentTaskList(process.getProcessInstanceId());

        // 5、计算当前用户是否可以审批，能够查看详情的用户不是都能审批，审批后也不能重复审批
        final String username = LoginUserInfoHelper.getUsername();
        boolean isApprove = taskList.stream()
                                    .anyMatch(task -> task.getAssignee()
                                                          .equals(username));

        // 6、组装审批详情信息
        Map<String, Object> map = new HashMap<>();
        map.put("process", process);
        map.put("processRecordList", processRecordList);
        map.put("processTemplate", processTemplate);
        map.put("isApprove", isApprove);
        return map;
    }

    /**
     * 审批
     *
     * @param approvalVO 批准 VO
     */
    @Override
    public void approve(ApprovalVO approvalVO) {
        // 获取基本信息
        final String taskId = approvalVO.getTaskId();
        final Long processId = approvalVO.getProcessId();

        // 获取流程变量
        final Map<String, Object> variables = taskService.getVariables(taskId);
        variables.forEach((key, value) -> log.info("Key = " + key + ", Value = " + value));

        // 同意
        if (approvalVO.getStatus() == 1) {
            final Map<String, Object> variablesTemp = new HashMap<>();
            taskService.complete(taskId, variablesTemp);
        }
        // 驳回
        else {
            this.endTask(taskId);
        }

        // 保存审批记录
        final String description = approvalVO.getStatus() == 1 ?
                "同意" :
                "驳回";
        processRecordService.record(processId, approvalVO.getStatus(), description);

        // 计算下一个审批人
        final Process process = this.getById(processId);
        final List<Task> taskList = this.getCurrentTaskList(process.getProcessInstanceId());
        if (!CollectionUtils.isEmpty(taskList)) {
            // 流程负责人
            final String[] assigneeArr = taskList.stream()
                                                 .map(task -> {
                                                     final String assignee = task.getAssignee();
                                                     final SysUser user = sysUserService.getByUsername(assignee);
                                                     if (user == null) {
                                                         return assignee;
                                                     }
                                                     return user.getName();
                                                 })
                                                 .toArray(String[]::new);
            process.setDescription("等待" + StringUtils.join(assigneeArr, "、") + "审批");
            // 状态（0：默认 1：审批中 2：审批通过 -1：驳回）
            process.setStatus(1);
        }
        else if (process.getStatus() == 1) {
            process.setDescription("审批完成（同意）");
            process.setStatus(2);
        }
        else {
            process.setDescription("审批完成（驳回）");
            process.setStatus(-1);
        }

        // 更新流程信息，推送消息给申请人
        this.updateById(process);
    }

    /**
     * 查询已处理活动
     *
     * @param pageParam 页面参数
     * @return {@link IPage}<{@link ProcessVO}>
     */
    @Override
    public IPage<ProcessVO> findProcessed(IPage<Process> pageParam) {
        // 获取基本信息
        final long current = pageParam.getCurrent();
        final long size = pageParam.getSize();

        // 根据当前人的ID分页查询历史已处理活动
        final HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery()
                                                                                  .taskAssignee(LoginUserInfoHelper.getUsername())
                                                                                  .finished()
                                                                                  .orderByTaskCreateTime()
                                                                                  .desc();
        final long start = (current - 1) * size;
        final List<HistoricTaskInstance> historicTaskInstanceList = historicTaskInstanceQuery.listPage((int) start, (int) size);
        final long totalCount = historicTaskInstanceQuery.count();
        if (totalCount == 0) {
            return new Page<>(current, size, totalCount);
        }

        // 转换为VO
        final Function<HistoricTaskInstance, ProcessVO> function = item -> {
            final String processInstanceId = item.getProcessInstanceId();
            final Process process = this.getOne(new LambdaQueryWrapper<Process>().eq(Process::getProcessInstanceId, processInstanceId));
            ProcessVO processVO = new ProcessVO();
            if (process == null) {
                return null;
            }
            BeanUtils.copyProperties(process, processVO);
            processVO.setTaskId("0");
            return processVO;
        };
        final List<ProcessVO> processVOList = historicTaskInstanceList.stream()
                                                                      .map(function)
                                                                      .collect(Collectors.toList());

        // 转换为分页对象
        return new Page<ProcessVO>(current, size, totalCount).setRecords(processVOList);
    }

    /**
     * 查询已发起活动
     *
     * @param pageParam 页面参数
     * @return {@link IPage}<{@link ProcessVO}>
     */
    @Override
    public IPage<ProcessVO> findStarted(Page<ProcessVO> pageParam) {
        final ProcessQueryVO processQueryVO = new ProcessQueryVO();
        processQueryVO.setUserId(LoginUserInfoHelper.getUserId());
        final IPage<ProcessVO> page = baseMapper.selectPage(pageParam, processQueryVO);
        page.getRecords()
            .forEach(item -> item.setTaskId("0"));
        return page;
    }

    private void endTask(String taskId) {
        // 当前任务
        final Task task = taskService.createTaskQuery()
                                     .taskId(taskId)
                                     .singleResult();

        // 并行任务
        final BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
        final List<EndEvent> eventEventList = bpmnModel.getMainProcess()
                                                       .findFlowElementsOfType(EndEvent.class);
        if (CollectionUtils.isEmpty(eventEventList)) {
            return;
        }
        final FlowNode endFlowNode = eventEventList.get(0);
        final FlowNode currentFlowNode = (FlowNode) bpmnModel.getMainProcess()
                                                             .getFlowElement(task.getTaskDefinitionKey());

        // 临时保存当前活动的原始方向
        List<SequenceFlow> sequenceFlowList = currentFlowNode.getOutgoingFlows();
        List<SequenceFlow> originalSequenceFlowList = new ArrayList<>(sequenceFlowList);
        originalSequenceFlowList.forEach(sequenceFlow -> log.info("原始方向：" + sequenceFlow));

        //  清理活动方向
        sequenceFlowList.clear();

        // 建立新方向
        SequenceFlow newSequenceFlow = new SequenceFlow();
        newSequenceFlow.setId("newSequenceFlowId");
        newSequenceFlow.setSourceFlowElement(currentFlowNode);
        newSequenceFlow.setTargetFlowElement(endFlowNode);
        List newSequenceFlowList = new ArrayList<>();
        newSequenceFlowList.add(newSequenceFlow);
        //  当前节点指向新的方向
        currentFlowNode.setOutgoingFlows(newSequenceFlowList);

        //  完成当前任务
        taskService.complete(task.getId());
    }

    /**
     * 获取当前任务列表
     *
     * @param processInstanceId 流程实例 ID
     * @return {@link List}<{@link Task}>
     */
    private List<Task> getCurrentTaskList(String processInstanceId) {
        return taskService.createTaskQuery()
                          .processInstanceId(processInstanceId)
                          .list();
    }
}
