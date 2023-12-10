package xyz.funnyboy.common.handler;

import lombok.Data;
import xyz.funnyboy.common.result.ResultCodeEnum;

@Data
public class VectorXException extends RuntimeException
{
    private static final long serialVersionUID = 6315979060669302649L;

    private Integer code;

    private String message;

    public VectorXException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 接收枚举类型对象
     *
     * @param resultCodeEnum
     */
    public VectorXException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    @Override
    public String toString() {
        return "VectorXException{" + "code=" + code + ", message='" + message + '\'' + '}';
    }
}
