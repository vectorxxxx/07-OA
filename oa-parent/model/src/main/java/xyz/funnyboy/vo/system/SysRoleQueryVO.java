//
//
package xyz.funnyboy.vo.system;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 角色查询实体
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */

@Data
public class SysRoleQueryVO implements Serializable
{

    private static final long serialVersionUID = 186363419859022812L;

    private String roleName;
}

