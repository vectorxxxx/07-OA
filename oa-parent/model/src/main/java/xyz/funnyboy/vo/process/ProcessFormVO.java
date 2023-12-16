package xyz.funnyboy.vo.process;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author VectorX
 * @version V1.0
 * @description 流程表单VO
 * @date 16/12/2023
 */
@ApiModel(description = "流程表单")
@Data
public class ProcessFormVO
{
    @ApiModelProperty(value = "审批模板id")
    private Long processTemplateId;

    @ApiModelProperty(value = "审批类型id")
    private Long processTypeId;

    @ApiModelProperty(value = "表单值")
    private String formValues;
}
