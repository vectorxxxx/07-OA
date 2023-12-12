package xyz.funnyboy.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.funnyboy.common.result.Result;

@ControllerAdvice
public class GlobalExceptionHandler
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<String> error(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return Result.fail();
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result<Object> error(ArithmeticException e) {
        LOGGER.error(e.getMessage(), e);
        return Result.fail()
                     .message(e.getMessage());
    }

    @ExceptionHandler(VectorXException.class)
    @ResponseBody
    public Result<Object> error(VectorXException e) {
        LOGGER.error(e.getMessage(), e);
        return Result.fail()
                     .code(e.getCode())
                     .message(e.getMessage());
    }
}
