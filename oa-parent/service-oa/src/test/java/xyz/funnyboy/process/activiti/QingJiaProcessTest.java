package xyz.funnyboy.process.activiti;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.zip.ZipInputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QingJiaProcessTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(QingJiaProcessTest.class);

    private static final String DEPLOY_NAME = "请假申请流程";
    private static final String RESOURCE_PROCESS_BPMN_XML = "process/qingjia.bpmn20.xml";
    private static final String RESOURCE_PROCESS_PNG = "process/qingjia.png";
    private static final String RESOURCE_PROCESS_ZIP = "process/qingjia.zip";
    private static final String PROCESS_DEFINITION_KEY = "qingjia";
    private static final String ASSIGNEE = "zhangsan";

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    /**
     * 流程部署-单个文件部署方式
     */
    @Test
    public void testDeployProcess() {
        // 流程部署
        final Deployment deploy = repositoryService.createDeployment()
                                                   .addClasspathResource(RESOURCE_PROCESS_BPMN_XML)
                                                   .addClasspathResource(RESOURCE_PROCESS_PNG)
                                                   .name(DEPLOY_NAME)
                                                   .deploy();
        LOGGER.info("流程部署ID：" + deploy.getId());
        LOGGER.info("流程部署Name：" + deploy.getName());
    }

    /**
     * 流程部署-压缩包部署方式
     */
    @Test
    public void testDeployProcessByZip() {
        // zip 输入流
        final InputStream inputStream = getClass().getClassLoader()
                                                  .getResourceAsStream(RESOURCE_PROCESS_ZIP);
        final ZipInputStream zipInputStream = new ZipInputStream(Objects.requireNonNull(inputStream));

        // 流程部署
        final Deployment deploy = repositoryService.createDeployment()
                                                   .addZipInputStream(zipInputStream)
                                                   .name(DEPLOY_NAME)
                                                   .deploy();
        LOGGER.info("流程部署ID：" + deploy.getId());
        LOGGER.info("流程部署Name：" + deploy.getName());
    }

    /**
     * 查询流程定义
     */
    @Test
    public void testFindProcessDefinitionList() {
        final List<ProcessDefinition> definitionList = repositoryService.createProcessDefinitionQuery()
                                                                        .orderByProcessDefinitionVersion()
                                                                        .desc()
                                                                        .list();
        definitionList.forEach(definition -> {
            LOGGER.info("流程定义ID：" + definition.getId());
            LOGGER.info("流程定义Key：" + definition.getKey());
            LOGGER.info("流程定义名称：" + definition.getName());
            LOGGER.info("流程定义版本：" + definition.getVersion());
            LOGGER.info("流程定义描述：" + definition.getDescription());
            LOGGER.info("流程定义部署ID：" + definition.getDeploymentId());
            LOGGER.info("流程定义资源名称：" + definition.getResourceName());
            LOGGER.info("==============================================");
        });
    }

    /**
     * 删除流程定义
     */
    @Test
    public void testDeleteDeployment() {
        final ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                                                                     .latestVersion()
                                                                     .singleResult();
        String deploymentId = processDefinition.getDeploymentId();
        repositoryService.deleteDeployment(deploymentId, true);
    }

    /**
     * 流程定义激活挂起
     * <p>
     * 流程定义为挂起状态，该流程定义将不允许启动新的流程实例，同时该流程定义下所有的流程实例将全部挂起暂停执行
     */
    @Test
    public void testSuspendProcessDefinition() {
        final ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                                                                     .processDefinitionKey(PROCESS_DEFINITION_KEY)
                                                                     .latestVersion()
                                                                     .singleResult();
        final String processDefinitionId = processDefinition.getId();
        if (processDefinition.isSuspended()) {
            repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
            System.out.println("流程定义:" + processDefinitionId + "激活");
        }
        else {
            repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
            System.out.println("流程定义:" + processDefinitionId + "挂起");
        }
    }

    /**
     * 流程实例激活挂起
     */
    @Test
    public void testSuspendProcessInstance() {
        String processInstanceId = "cd14439e-99bd-11ee-b08e-da80834f07a5";
        final ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                                                              .processInstanceId(processInstanceId)
                                                              .singleResult();
        if (processInstance.isSuspended()) {
            runtimeService.activateProcessInstanceById(processInstanceId);
            System.out.println("流程实例:" + processInstanceId + "激活");
        }
        else {
            runtimeService.suspendProcessInstanceById(processInstanceId);
            System.out.println("流程实例:" + processInstanceId + "挂起");
        }
    }

    /**
     * 启动流程实例
     */
    @Test
    public void testStartUpProcess() {
        // 创建流程实例
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY);
        // 输出实例相关信息
        LOGGER.info("流程定义ID：" + processInstance.getProcessDefinitionId());
        LOGGER.info("流程实例ID：" + processInstance.getId());
        LOGGER.info("当前活动ID：" + processInstance.getActivityId());
    }

    /**
     * 启动流程实例，添加businessKey
     */
    @Test
    public void testStartUpProcessAddBusinessKey() {
        final String businessKey = UUID.randomUUID()
                                       .toString();
        LOGGER.info("businessKey：" + businessKey);
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY, businessKey);
        LOGGER.info("业务ID：" + processInstance.getBusinessKey());
    }

    /**
     * 查询当前个人待执行的任务
     */
    @Test
    public void testFindPendingTaskList() {
        final List<Task> taskList = taskService.createTaskQuery()
                                               .taskAssignee(ASSIGNEE)
                                               .list();
        taskList.forEach(task -> {
            LOGGER.info("流程定义ID：" + task.getProcessDefinitionId());
            LOGGER.info("流程实例ID：" + task.getProcessInstanceId());
            LOGGER.info("任务ID：" + task.getId());
            LOGGER.info("任务名称：" + task.getName());
            LOGGER.info("任务负责人：" + task.getAssignee());
            LOGGER.info("业务ID：" + task.getBusinessKey());
            LOGGER.info("==============================================");
        });
    }

    /**
     * 完成任务
     */
    @Test
    public void testCompleteTask() {
        final Task task = taskService.createTaskQuery()
                                     .taskAssignee(ASSIGNEE)
                                     .singleResult();
        taskService.complete(task.getId());
    }

    /**
     * 查询已处理历史任务
     */
    @Test
    public void testFindProcessedTaskList() {
        final List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery()
                                                                                  .taskAssignee(ASSIGNEE)
                                                                                  .finished()
                                                                                  .list();
        historicTaskInstanceList.forEach(historicTaskInstance -> {
            LOGGER.info("流程定义ID：" + historicTaskInstance.getProcessDefinitionId());
            LOGGER.info("流程实例ID：" + historicTaskInstance.getProcessInstanceId());
            LOGGER.info("任务ID：" + historicTaskInstance.getId());
            LOGGER.info("任务名称：" + historicTaskInstance.getName());
            LOGGER.info("任务负责人：" + historicTaskInstance.getAssignee());
            LOGGER.info("==============================================");
        });
    }

}
