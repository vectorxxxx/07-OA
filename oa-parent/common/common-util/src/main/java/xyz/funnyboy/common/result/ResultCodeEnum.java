package xyz.funnyboy.common.result;

import lombok.Getter;

@Getter
public enum ResultCodeEnum
{
    SUCCESS(200, "成功"),

    FAIL(500, "失败"),

    LOGIN_ERROR(503, "登录失败"),

    PERMISSION(403, "认证失败"),

    ;

    private Integer code;

    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
