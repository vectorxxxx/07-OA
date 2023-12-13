package xyz.funnyboy.common.result;

import lombok.Getter;

@Getter
public enum ResultCodeEnum
{
    SUCCESS(200, "成功"),

    FAIL(500, "失败"),

    LOGIN_ERROR(503, "登录失败"),

    AUTHENTICATION_FAILED(401, "认证失败"),

    NO_PERMISSION(403, "没有权限"),

    ;

    private Integer code;

    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
