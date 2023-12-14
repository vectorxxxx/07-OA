package xyz.funnyboy.model.process;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xyz.funnyboy.model.base.BaseEntity;

import java.util.List;

@ApiModel(description = "ProcessType")
@TableName("oa_process_type")
@Data
public class ProcessType extends BaseEntity
{

	private static final long serialVersionUID = -3698513380442852254L;

	@ApiModelProperty(value = "类型名称")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "描述")
	@TableField("description")
	private String description;

	@TableField(exist = false)
	private List<ProcessTemplate> processTemplateList;
}
