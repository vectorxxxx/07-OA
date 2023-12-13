package xyz.funnyboy.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import xyz.funnyboy.common.result.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author VectorX
 * @version 1.0.0
 * @description 响应工具类
 * @date 2023/12/13
 */
public class ResponseUtil
{

    public static void out(HttpServletResponse response, Result<Object> r) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            mapper.writeValue(response.getWriter(), r);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
