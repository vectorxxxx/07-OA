package xyz.funnyboy.process.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.funnyboy.common.result.Result;
import xyz.funnyboy.model.process.ProcessTemplate;
import xyz.funnyboy.process.service.ProcessTemplateService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Api(value = "审批模板管理",
     tags = "审批模板管理")
@RestController
@RequestMapping(value = "/admin/process/processTemplate")
public class ProcessTemplateController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessTemplateController.class);

    @Autowired
    private ProcessTemplateService processTemplateService;

    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    @PreAuthorize("hasAuthority('bnt.processTemplate.list')")
    public Result<Object> index(
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
            Page<ProcessTemplate> pageParam = new Page<>(page, limit);
            IPage<ProcessTemplate> pageModel = processTemplateService.selectPage(pageParam);
            return Result.ok(pageModel);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    @PreAuthorize("hasAuthority('bnt.processTemplate.list')")
    public Result<Object> get(
            @PathVariable
                    Long id) {
        try {
            ProcessTemplate processTemplate = processTemplateService.getById(id);
            return Result.ok(processTemplate);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "新增")
    @PostMapping("save")
    @PreAuthorize("hasAuthority('bnt.processTemplate.templateSet')")
    public Result<Object> save(
            @RequestBody
                    ProcessTemplate processTemplate) {
        try {
            final boolean save = processTemplateService.save(processTemplate);
            return save ?
                    Result.ok() :
                    Result.fail()
                          .message("新增失败");
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    @PreAuthorize("hasAuthority('bnt.processTemplate.templateSet')")
    public Result<Object> updateById(
            @RequestBody
                    ProcessTemplate processTemplate) {
        try {
            final boolean update = processTemplateService.updateById(processTemplate);
            return update ?
                    Result.ok() :
                    Result.fail()
                          .message("修改失败");
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    @PreAuthorize("hasAuthority('bnt.processTemplate.remove')")
    public Result<Object> remove(
            @PathVariable
                    Long id) {
        try {
            final boolean remove = processTemplateService.removeById(id);
            return remove ?
                    Result.ok() :
                    Result.fail()
                          .message("删除失败");
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "上传流程定义")
    @PostMapping("/uploadProcessDefinition")
    @PreAuthorize("hasAuthority('bnt.processTemplate.templateSet')")
    public Result<Object> uploadProcessDefinition(MultipartFile file) throws FileNotFoundException {
        // 获取上传文件名称
        final String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.trim()
                                        .length() == 0) {
            return Result.fail()
                         .message("上传失败：文件名为空");
        }

        // 获取 resources 目录的绝对路径
        final String resourcesRootPath = new File(ResourceUtils.getURL("classpath:")
                                                               .getPath()).getAbsolutePath();
        // 创建 process 目录
        final File processDir = new File(Paths.get(resourcesRootPath, "process")
                                              .toString());
        if (!processDir.exists()) {
            processDir.mkdirs();
        }
        // 创建 image 文件
        final File imageFile = new File(Paths.get(processDir.getAbsolutePath(), fileName)
                                             .toString());
        // 保存文件流到本地
        try {
            file.transferTo(imageFile);
        }
        catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return Result.fail()
                         .message("上传失败：" + e.getMessage());
        }

        // 根据上传地址后续部署流程定义，文件名称为流程定义的默认key
        final String processDefinitionPath = Paths.get("process", fileName)
                                                  .toString();
        final String processDefinitionKey = fileName.substring(0, fileName.lastIndexOf("."));

        // 回传数据
        Map<String, Object> map = new HashMap<>();
        map.put("processDefinitionPath", processDefinitionPath);
        map.put("processDefinitionKey", processDefinitionKey);
        return Result.ok(map);
    }

    @ApiOperation(value = "发布")
    @GetMapping("/publish/{id}")
    @PreAuthorize("hasAuthority('bnt.processTemplate.publish')")
    public Result<Object> publish(
            @PathVariable
                    Long id) {
        try {
            processTemplateService.publish(id);
            return Result.ok();
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }
}
