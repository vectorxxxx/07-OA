package xyz.funnyboy.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5
{

    private static final Logger LOGGER = LoggerFactory.getLogger(MD5.class);
    private static final char[] HEX_CHARS = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final String MD_5 = "MD5";

    public static String encrypt(String strSrc) {
        try {
            byte[] bytes = strSrc.getBytes();
            MessageDigest md = MessageDigest.getInstance(MD_5);
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (byte b : bytes) {
                chars[k++] = HEX_CHARS[b >>> 4 & 0xf];
                chars[k++] = HEX_CHARS[b & 0xf];
            }
            return new String(chars);
        }
        catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("MD5加密出错！！+" + e);
        }
    }
}
