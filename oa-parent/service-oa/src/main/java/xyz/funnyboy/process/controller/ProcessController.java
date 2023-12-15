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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.funnyboy.common.result.Result;
import xyz.funnyboy.process.service.ProcessService;
import xyz.funnyboy.vo.process.ProcessQueryVO;
import xyz.funnyboy.vo.process.ProcessVO;

@Api(tags = "审批流管理")
@RestController
@RequestMapping(value = "/admin/process")
public class ProcessController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessController.class);

    @Autowired
    private ProcessService processService;

    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    @PreAuthorize("hasAuthority('bnt.process.list')")
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
                    Long limit,

            @ApiParam(name = "processQueryVO",
                      value = "查询对象",
                      required = false)
                    ProcessQueryVO processQueryVO) {
        try {
            Page<ProcessVO> pageParam = new Page<>(page, limit);
            final IPage<ProcessVO> selectPage = processService.selectPage(pageParam, processQueryVO);
            return Result.ok(selectPage);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }
}
