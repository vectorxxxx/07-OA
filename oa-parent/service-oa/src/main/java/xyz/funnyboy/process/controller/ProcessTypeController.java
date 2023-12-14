package xyz.funnyboy.process.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.common.result.Result;
import xyz.funnyboy.model.process.ProcessType;
import xyz.funnyboy.process.service.ProcessTypeService;

@Api(value = "审批类型",
     tags = "审批类型")
@RestController
@RequestMapping("/admin/process/processType")
public class ProcessTypeController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessTypeController.class);

    @Autowired
    private ProcessTypeService processTypeService;

    @ApiOperation(value = "获取全部审批分类")
    @GetMapping("findAll")
    public Result<Object> findAll() {
        try {
            return Result.ok(processTypeService.list());
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    @PreAuthorize("hasAuthority('bnt.processType.list')")
    public Result<Object> index(
            @PathVariable
                    Long page,
            @PathVariable
                    Long limit) {
        try {
            Page<ProcessType> pageParam = new Page<>(page, limit);
            IPage<ProcessType> pageModel = processTypeService.page(pageParam);
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
    @PreAuthorize("hasAuthority('bnt.processType.list')")
    public Result<Object> get(
            @PathVariable
                    Long id) {
        try {
            ProcessType processType = processTypeService.getById(id);
            return Result.ok(processType);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "新增")
    @PostMapping("save")
    @PreAuthorize("hasAuthority('bnt.processType.add')")
    public Result<Object> save(
            @RequestBody
                    ProcessType processType) {
        try {
            final boolean save = processTypeService.save(processType);
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
    @PreAuthorize("hasAuthority('bnt.processType.update')")
    public Result<Object> updateById(
            @RequestBody
                    ProcessType processType) {
        try {
            final boolean update = processTypeService.updateById(processType);
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
    @PreAuthorize("hasAuthority('bnt.processType.remove')")
    public Result<Object> remove(
            @PathVariable
                    Long id) {
        try {
            final boolean remove = processTypeService.removeById(id);
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
}
