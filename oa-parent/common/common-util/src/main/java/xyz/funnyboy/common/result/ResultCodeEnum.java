package xyz.funnyboy.common.result;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(501, "失败"),
    SERVICE_ERROR(500, "服务异常"),
    DATA_ERROR(503, "数据异常"),

    LOGIN_AUTH(401, "未登陆"),
    PERMISSION(403, "没有权限")
    ;

    private Integer code;

    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
