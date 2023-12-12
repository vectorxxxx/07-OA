package xyz.funnyboy.common.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Date;

public class JwtHelper
{
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtHelper.class);
    private static final long TOKEN_EXPIRATION = 365L * 24 * 60 * 60 * 1000;
    private static final String TOKEN_SIGN_KEY = "123456";

    public static String createToken(Long userId, String username) {
        return Jwts.builder()
                   .setSubject("AUTH-USER")
                   .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                   .claim("userId", userId)
                   .claim("username", username)
                   .signWith(SignatureAlgorithm.HS512, TOKEN_SIGN_KEY)
                   .compressWith(CompressionCodecs.GZIP)
                   .compact();
    }

    public static Long getUserId(String token) {
        try {
            if (StringUtils.isEmpty(token)) {
                return null;
            }

            final Jws<Claims> claimsJws = Jwts.parser()
                                              .setSigningKey(TOKEN_SIGN_KEY)
                                              .parseClaimsJws(token);
            final Integer userId = (Integer) claimsJws.getBody()
                                                      .get("userId");
            return userId.longValue();
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    public static String getUsername(String token) {
        try {
            if (StringUtils.isEmpty(token)) {
                return null;
            }

            final Jws<Claims> claimsJws = Jwts.parser()
                                              .setSigningKey(TOKEN_SIGN_KEY)
                                              .parseClaimsJws(token);
            return (String) claimsJws.getBody()
                                     .get("username");
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }
}
