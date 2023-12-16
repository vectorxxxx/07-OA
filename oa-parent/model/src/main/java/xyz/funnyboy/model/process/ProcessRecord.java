package xyz.funnyboy.model.process;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xyz.funnyboy.model.base.BaseEntity;

@ApiModel(description = "ProcessRecord")
@TableName("oa_process_record")
@Data
public class ProcessRecord extends BaseEntity
{

    private static final long serialVersionUID = 8618714600602971249L;

    @ApiModelProperty(value = "审批流程id")
    @TableField("process_id")
    private Long processId;

    @ApiModelProperty(value = "审批描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "操作用户id")
    @TableField("operate_user_id")
    private Long operateUserId;

    @ApiModelProperty(value = "操作用户")
    @TableField("operate_user")
    private String operateUser;

}
