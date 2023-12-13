package xyz.funnyboy.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import xyz.funnyboy.common.constant.CommonConstant;
import xyz.funnyboy.common.jwt.JwtHelper;
import xyz.funnyboy.common.result.Result;
import xyz.funnyboy.common.result.ResultCodeEnum;
import xyz.funnyboy.common.util.ResponseUtil;
import xyz.funnyboy.model.system.SysUser;
import xyz.funnyboy.security.custom.CustomUser;
import xyz.funnyboy.vo.system.LoginVO;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author VectorX
 * @version 1.0.0
 * @description 登录过滤器
 * @date 2023/12/13
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter
{

    private static final String HTTP_METHOD = "POST";

    public TokenLoginFilter(AuthenticationManager authenticationManager) {
        this.setAuthenticationManager(authenticationManager);
        this.setPostOnly(false);
        // 指定登录接口及提交方式，可以指定任意路径
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(CommonConstant.LOGIN, HTTP_METHOD));
    }

    /**
     * 登录认证
     *
     * @param request
     * @param response
     * @return {@link Authentication}
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            final LoginVO loginVO = new ObjectMapper().readValue(request.getInputStream(), LoginVO.class);
            final Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginVO.getUsername(), loginVO.getPassword());
            return this.getAuthenticationManager()
                       .authenticate(authenticationToken);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 登录成功
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        final CustomUser customUser = (CustomUser) authResult.getPrincipal();
        final SysUser sysUser = customUser.getSysUser();
        final String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        ResponseUtil.out(response, Result.ok(map));
    }

    /**
     * 登录失败
     *
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        if (failed.getCause() instanceof RuntimeException) {
            ResponseUtil.out(response, Result.build(null, 500, failed.getMessage()));
        }
        else {
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.LOGIN_ERROR));
        }
    }
}
