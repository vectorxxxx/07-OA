package xyz.funnyboy.model.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import xyz.funnyboy.model.base.BaseEntity;

@Data
@TableName("sys_role")
public class SysRole extends BaseEntity
{

    private static final long serialVersionUID = -1436852803963535111L;

    //角色名称
    @TableField("role_name")
    private String roleName;

    //角色编码
    @TableField("role_code")
    private String roleCode;

    //描述
    @TableField("description")
    private String description;

}
