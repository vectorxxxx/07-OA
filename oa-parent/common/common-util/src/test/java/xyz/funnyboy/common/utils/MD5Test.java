package xyz.funnyboy.common.utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.funnyboy.common.util.MD5;

@SpringBootTest(classes = MD5.class)
public class MD5Test
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MD5Test.class);

    @Test
    public void testEncrypt() {
        LOGGER.info(MD5.encrypt("111111"));
    }
}
