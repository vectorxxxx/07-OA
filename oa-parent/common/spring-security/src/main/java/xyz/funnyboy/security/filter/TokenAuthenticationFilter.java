package xyz.funnyboy.security.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.funnyboy.common.constant.CommonConstant;
import xyz.funnyboy.common.jwt.JwtHelper;
import xyz.funnyboy.common.result.Result;
import xyz.funnyboy.common.result.ResultCodeEnum;
import xyz.funnyboy.common.util.ResponseUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class TokenAuthenticationFilter extends OncePerRequestFilter
{

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
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.PERMISSION));
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
        final String username = JwtHelper.getUsername(token);
        logger.info("username=" + username);
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        // 返回 UsernamePasswordAuthenticationToken
        return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
    }
}
