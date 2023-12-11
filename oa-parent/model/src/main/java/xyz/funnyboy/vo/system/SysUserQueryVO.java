package xyz.funnyboy.vo.system;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserQueryVO implements Serializable
{
    private static final long serialVersionUID = -6867725986763765018L;

    private String keyword;

    private String createTimeBegin;
    private String createTimeEnd;

    private String roleId;
    private String postId;
    private String deptId;
}
