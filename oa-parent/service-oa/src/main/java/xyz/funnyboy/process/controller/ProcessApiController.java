package xyz.funnyboy.process.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.common.result.Result;
import xyz.funnyboy.model.process.Process;
import xyz.funnyboy.model.process.ProcessTemplate;
import xyz.funnyboy.model.process.ProcessType;
import xyz.funnyboy.process.service.ProcessService;
import xyz.funnyboy.process.service.ProcessTemplateService;
import xyz.funnyboy.process.service.ProcessTypeService;
import xyz.funnyboy.vo.process.ApprovalVO;
import xyz.funnyboy.vo.process.ProcessFormVO;
import xyz.funnyboy.vo.process.ProcessVO;

import java.util.List;
import java.util.Map;

@Api(tags = "审批流管理")
@RestController
@RequestMapping(value = "/admin/process")
// 跨域
@CrossOrigin
public class ProcessApiController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessApiController.class);

    @Autowired
    private ProcessTypeService processTypeService;

    @Autowired
    private ProcessTemplateService processTemplateService;

    @Autowired
    private ProcessService processService;

    @ApiOperation(value = "获取全部审批分类及模板")
    @GetMapping("findProcessType")
    public Result<Object> findProcessType() {
        try {
            final List<ProcessType> processTypeList = processTypeService.findProcessType();
            return Result.ok(processTypeList);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "获取审批模板")
    @GetMapping("getProcessTemplate/{processTemplateId}")
    public Result<Object> get(
            @PathVariable
                    Long processTemplateId) {
        try {
            ProcessTemplate processTemplate = processTemplateService.getById(processTemplateId);
            return Result.ok(processTemplate);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "启动流程")
    @PostMapping("/startUp")
    public Result<Object> start(
            @RequestBody
                    ProcessFormVO processFormVO) {
        try {
            processService.startUp(processFormVO);
            return Result.ok();
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "待处理")
    @GetMapping("/findPending/{page}/{limit}")
    public Result<Object> findPending(
            @ApiParam(name = "page",
                      value = "当前页码",
                      required = true)
            @PathVariable
                    Long page,

            @ApiParam(name = "limit",
                      value = "每页记录数",
                      required = true)
            @PathVariable
                    Long limit) {
        try {
            final Page<ProcessVO> pageParam = new Page<>(page, limit);
            final IPage<ProcessVO> processVOIPage = processService.findPending(pageParam);
            return Result.ok(processVOIPage);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "获取审批详情")
    @GetMapping("show/{id}")
    public Result<Object> show(
            @PathVariable
                    Long id) {
        try {
            final Map<String, Object> detailInfo = processService.show(id);
            return Result.ok(detailInfo);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "审批")
    @PostMapping("approve")
    public Result<Object> approve(
            @RequestBody
                    ApprovalVO approvalVO) {
        try {
            processService.approve(approvalVO);
            return Result.ok();
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "已处理")
    @GetMapping("/findProcessed/{page}/{limit}")
    public Result<Object> findProcessed(
            @ApiParam(name = "page",
                      value = "当前页码",
                      required = true)
            @PathVariable
                    Long page,
            @ApiParam(name = "limit",
                      value = "每页记录数",
                      required = true)
            @PathVariable
                    Long limit) {
        try {
            IPage<Process> pageParam = new Page<>(page, limit);
            final IPage<ProcessVO> processed = processService.findProcessed(pageParam);
            return Result.ok(processed);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "已发起")
    @GetMapping("/findStarted/{page}/{limit}")
    public Result<Object> findStarted(
            @ApiParam(name = "page",
                      value = "当前页码",
                      required = true)
            @PathVariable
                    Long page,

            @ApiParam(name = "limit",
                      value = "每页记录数",
                      required = true)
            @PathVariable
                    Long limit) {
        try {
            Page<ProcessVO> pageParam = new Page<>(page, limit);
            final IPage<ProcessVO> started = processService.findStarted(pageParam);
            return Result.ok(started);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }
}
