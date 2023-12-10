package xyz.funnyboy.common.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.funnyboy.common.result.Result;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<String> error(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result<Object> error(ArithmeticException e) {
        e.printStackTrace();
        return Result
                .fail()
                .message(e.getMessage());
    }

    @ExceptionHandler(VectorXException.class)
    @ResponseBody
    public Result<Object> error(VectorXException e) {
        e.printStackTrace();
        return Result
                .fail()
                .code(e.getCode())
                .message(e.getMessage());
    }
}
