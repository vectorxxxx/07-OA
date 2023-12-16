package xyz.funnyboy.common.jwt;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = JwtHelper.class)
public class JwtHelperTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtHelperTest.class);

    @Test
    public void test() {
        final String token = JwtHelper.createToken(13L, "admin");
        LOGGER.info(token);
        LOGGER.info(String.valueOf(JwtHelper.getUserId(token)));
        LOGGER.info(JwtHelper.getUsername(token));
    }

    @Test
    public void test2() {
        String token = JwtHelper.createToken(13L, "admin");
        LOGGER.info(token);
        token = JwtHelper.createToken(14L, "zhangsan");
        LOGGER.info(token);
        token = JwtHelper.createToken(15L, "lisi");
        LOGGER.info(token);
        token = JwtHelper.createToken(16L, "test3");
        LOGGER.info(token);
        token = JwtHelper.createToken(17L, "test4");
        LOGGER.info(token);
        token = JwtHelper.createToken(18L, "zhangsan01");
        LOGGER.info(token);
    }
}
