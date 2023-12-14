package xyz.funnyboy.process.activiti;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JiaBanProcessTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(JiaBanProcessTest.class);

    private static final String DEPLOY_NAME = "加班申请流程";
    private static final String RESOURCE_PROCESS_BPMN_XML_01 = "process/jiaban01.bpmn20.xml";
    private static final String RESOURCE_PROCESS_BPMN_XML_02 = "process/jiaban02.bpmn20.xml";
    private static final String RESOURCE_PROCESS_BPMN_XML_03 = "process/jiaban03.bpmn20.xml";
    private static final String RESOURCE_PROCESS_BPMN_XML_04 = "process/jiaban04.bpmn20.xml";
    private static final String PROCESS_DEFINITION_KEY_01 = "jiaban01";
    private static final String PROCESS_DEFINITION_KEY_02 = "jiaban02";
    private static final String PROCESS_DEFINITION_KEY_03 = "jiaban03";
    private static final String PROCESS_DEFINITION_KEY_04 = "jiaban04";

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    /**
     * 流程部署-单个文件部署方式
     */
    @Test
    public void testDeployProcess01() {
        // 流程部署
        final Deployment deploy = repositoryService.createDeployment()
                                                   .addClasspathResource(RESOURCE_PROCESS_BPMN_XML_01)
                                                   .name(DEPLOY_NAME)
                                                   .deploy();
        LOGGER.info("流程部署ID：" + deploy.getId());
        LOGGER.info("流程部署Name：" + deploy.getName());
    }

    /**
     * 启动流程实例-设置流程变量
     */
    @Test
    public void testStartUpProcess01() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("assignee1", "zhangsan");
        variables.put("assignee2", "lisi");
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY_01, variables);
        LOGGER.info("流程定义ID：" + processInstance.getProcessDefinitionId());
        LOGGER.info("流程实例ID：" + processInstance.getId());
    }

    /**
     * 流程部署-单个文件部署方式
     */
    @Test
    public void testDeployProcess02() {
        // 流程部署
        final Deployment deploy = repositoryService.createDeployment()
                                                   .addClasspathResource(RESOURCE_PROCESS_BPMN_XML_02)
                                                   .name(DEPLOY_NAME)
                                                   .deploy();
        LOGGER.info("流程部署ID：" + deploy.getId());
        LOGGER.info("流程部署Name：" + deploy.getName());
    }

    /**
     * 启动流程实例
     */
    @Test
    public void testStartUpProcess02() {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY_02);
        LOGGER.info("流程定义ID：" + processInstance.getProcessDefinitionId());
        LOGGER.info("流程实例ID：" + processInstance.getId());
    }

    /**
     * 流程部署-单个文件部署方式
     */
    @Test
    public void testDeployProcess03() {
        // 流程部署
        final Deployment deploy = repositoryService.createDeployment()
                                                   .addClasspathResource(RESOURCE_PROCESS_BPMN_XML_03)
                                                   .name(DEPLOY_NAME)
                                                   .deploy();
        LOGGER.info("流程部署ID：" + deploy.getId());
        LOGGER.info("流程部署Name：" + deploy.getName());
    }

    /**
     * 启动流程实例
     */
    @Test
    public void testStartUpProcess03() {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY_03);
        LOGGER.info("流程定义ID：" + processInstance.getProcessDefinitionId());
        LOGGER.info("流程实例ID：" + processInstance.getId());
    }

    /**
     * 任务办理时设置流程变量
     */
    @Test
    public void testCompleteTask() {
        final Task task = taskService.createTaskQuery()
                                     .taskAssignee("zhangsan")
                                     .orderByTaskCreateTime()
                                     .desc()
                                     .list()
                                     .get(0);
        Map<String, Object> variables = new HashMap<>();
        variables.put("assign2", "zhao");
        taskService.complete(task.getId(), variables);
    }

    /**
     * 当前流程实例设置流程变量
     */
    @Test
    public void testProcessInstanceIdSetVariables() {
        final String executionId = runtimeService.createProcessInstanceQuery()
                                                 .list()
                                                 .get(0)
                                                 .getId();
        Map<String, Object> variables = new HashMap<>();
        variables.put("assign2", "wang");
        runtimeService.setVariables(executionId, variables);
    }

    /**
     * 任务办理时设置local变量
     */
    @Test
    public void testCompleteLocalTask() {
        final Task task = taskService.createTaskQuery()
                                     .taskAssignee("zhangsan")
                                     .orderByTaskCreateTime()
                                     .desc()
                                     .list()
                                     .get(0);
        final String taskId = task.getId();
        final String variableName = "assignee02";
        taskService.setVariableLocal(taskId, variableName, "li");
        LOGGER.info(taskService.getVariableLocal(taskId, variableName)
                               .toString());
        taskService.complete(taskId);
    }

    /**
     * 部署启动
     */
    @Test
    public void testDeployProcessAndStartUpProcessInstance04() {
        final Deployment deploy = repositoryService.createDeployment()
                                                   .addClasspathResource(RESOURCE_PROCESS_BPMN_XML_04)
                                                   .name(DEPLOY_NAME)
                                                   .deploy();
        LOGGER.info("流程部署ID：" + deploy.getId());
        LOGGER.info("流程部署Name：" + deploy.getName());

        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY_04);
        LOGGER.info("流程定义ID：" + processInstance.getProcessDefinitionId());
        LOGGER.info("流程实例ID：" + processInstance.getId());
    }

    /**
     * 查询组任务
     */
    @Test
    public void testFindGroupTaskList() {
        final String candidateUser = "zhangsan01";
        final List<Task> taskList = taskService.createTaskQuery()
                                               .taskCandidateUser(candidateUser)
                                               .list();
        taskList.forEach(task -> {
            LOGGER.info("==============================================");
            LOGGER.info("流程实例ID：" + task.getProcessInstanceId());
            LOGGER.info("任务ID：" + task.getId());
            LOGGER.info("任务名称：" + task.getName());
            LOGGER.info("任务描述：" + task.getDescription());
            LOGGER.info("任务负责人：" + task.getAssignee());
        });
    }

    /**
     * 拾取任务
     */
    @Test
    public void testClaimTask() {
        final String candidateUser = "zhangsan01";
        final String candidateUser2 = "zhangsan02";
        final Task task = taskService.createTaskQuery()
                                     .taskCandidateUser(candidateUser)
                                     .singleResult();
        if (task != null) {
            taskService.claim(task.getId(), candidateUser);
            LOGGER.info("任务ID：" + task.getId() + " 被 " + candidateUser + " 签收");
            try {
                taskService.claim(task.getId(), candidateUser2);
            }
            catch (Exception e) {
                e.printStackTrace();
                LOGGER.info("任务ID：" + task.getId() + " 已拾取，无法被 " + candidateUser2 + " 签收");
            }
        }
    }

    /**
     * 查询个人待办任务
     */
    @Test
    public void testFindGroupPendingTaskList() {
        final String assignee = "zhangsan02";
        final List<Task> taskList = taskService.createTaskQuery()
                                               .taskAssignee(assignee)
                                               .list();
        taskList.forEach(task -> {
            LOGGER.info("==============================================");
            LOGGER.info("流程实例ID：" + task.getProcessInstanceId());
            LOGGER.info("任务ID：" + task.getId());
            LOGGER.info("任务名称：" + task.getName());
            LOGGER.info("任务描述：" + task.getDescription());
            LOGGER.info("任务负责人：" + task.getAssignee());
        });
    }

    /**
     * 办理个人任务
     */
    @Test
    public void testCompleteGroupTask() {
        final String assignee = "zhangsan01";
        final Task task = taskService.createTaskQuery()
                                     .taskAssignee(assignee)
                                     .singleResult();
        taskService.complete(task.getId());
    }

    /**
     * 归还任务
     */
    @Test
    public void testAssignToGroupTask() {
        final String taskId = "cdb98c5b-9a29-11ee-ae18-da80834f07a5";
        final String assignee = "zhangsan01";

        final Task task = taskService.createTaskQuery()
                                     .taskId(taskId)
                                     .taskAssignee(assignee)
                                     .singleResult();
        if (task != null) {
            taskService.setAssignee(task.getId(), null);
            LOGGER.info("任务ID：" + task.getId() + " 被 " + assignee + " 归还");
        }
    }

    /**
     * 任务交接
     */
    @Test
    public void testAssignToCandidateUser() {
        final String taskId = "cdb98c5b-9a29-11ee-ae18-da80834f07a5";
        final String assignee = "zhangsan01";

        final Task task = taskService.createTaskQuery()
                                     .taskId(taskId)
                                     .taskAssignee(assignee)
                                     .singleResult();
        if (task != null) {
            final String userId = "zhangsan02";
            taskService.setAssignee(task.getId(), userId);
            LOGGER.info("任务ID：" + task.getId() + " 被 " + assignee + " 交接给 " + userId);
        }
    }
}
