package xyz.funnyboy.security.filter;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.funnyboy.common.constant.CommonConstant;
import xyz.funnyboy.common.jwt.JwtHelper;
import xyz.funnyboy.common.result.Result;
import xyz.funnyboy.common.result.ResultCodeEnum;
import xyz.funnyboy.common.util.ResponseUtil;
import xyz.funnyboy.security.custom.LoginUserInfoHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class TokenAuthenticationFilter extends OncePerRequestFilter
{
    private StringRedisTemplate redisTemplate;

    public TokenAuthenticationFilter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.info("uri:" + request.getRequestURI());
        // 如果是登录接口，直接放行
        if (CommonConstant.LOGIN.equals(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        final UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if (authentication != null) {
            // 认证通过，将用户信息放入 SecurityContext
            logger.info("认证成功...");
            SecurityContextHolder.getContext()
                                 .setAuthentication(authentication);
            chain.doFilter(request, response);
        }
        else {
            // 认证失败，返回错误状态码
            logger.info("认证失败...");
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.AUTHENTICATION_FAILED));
        }
    }

    /**
     * 获取认证token
     *
     * @param request
     * @return {@link UsernamePasswordAuthenticationToken}
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // 取出 token
        final String token = request.getHeader("token");
        logger.info("token=" + token);
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        // 取出 username
        final Long userId = JwtHelper.getUserId(token);
        final String username = JwtHelper.getUsername(token);
        logger.info("username=" + username);
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        // 通过 ThreadLocal 记录当前登录信息
        LoginUserInfoHelper.setUserId(userId);
        LoginUserInfoHelper.setUsername(JwtHelper.getUsername(token));

        // 取出Redis缓存中的权限信息
        final String authoritiesStr = redisTemplate.opsForValue()
                                                   .get(username);
        List<SimpleGrantedAuthority> authorities = JSON.parseArray(authoritiesStr, SimpleGrantedAuthority.class);
        if (CollectionUtils.isEmpty(authorities)) {
            authorities = Collections.emptyList();
        }

        // 返回 UsernamePasswordAuthenticationToken
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }
}
