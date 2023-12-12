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
        final String token = JwtHelper.createToken(1L, "funnyboy");
        LOGGER.info(String.valueOf(JwtHelper.getUserId(token)));
        LOGGER.info(JwtHelper.getUsername(token));
    }
}
