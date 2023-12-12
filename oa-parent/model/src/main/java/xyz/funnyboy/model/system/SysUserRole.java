package xyz.funnyboy.model.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xyz.funnyboy.model.base.BaseEntity;

@ApiModel(description = "用户角色")
@Data
@TableName("sys_user_role")
public class SysUserRole extends BaseEntity
{
	private static final long serialVersionUID = -641474561691517717L;

	@ApiModelProperty(value = "角色id")
	@TableField("role_id")
	private Long roleId;

	@ApiModelProperty(value = "用户id")
	@TableField("user_id")
	private Long userId;
}

