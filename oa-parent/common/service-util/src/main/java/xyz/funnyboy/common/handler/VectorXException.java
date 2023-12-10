package xyz.funnyboy.common.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import xyz.funnyboy.common.result.ResultCodeEnum;

@Data
@AllArgsConstructor
public class VectorXException extends RuntimeException
{
    private static final long serialVersionUID = 6315979060669302649L;
    private Integer code;

    private String message;

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
}
