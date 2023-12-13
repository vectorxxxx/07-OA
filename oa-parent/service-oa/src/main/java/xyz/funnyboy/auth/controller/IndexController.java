package xyz.funnyboy.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.auth.service.SysUserService;
import xyz.funnyboy.common.handler.VectorXException;
import xyz.funnyboy.common.jwt.JwtHelper;
import xyz.funnyboy.common.result.Result;
import xyz.funnyboy.common.result.ResultCodeEnum;
import xyz.funnyboy.common.util.MD5;
import xyz.funnyboy.model.system.SysUser;
import xyz.funnyboy.vo.system.LoginVO;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "后台登录管理")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public Result<Map<String, Object>> login(
            @RequestBody
                    LoginVO loginVO) {
        SysUser sysUser = sysUserService.getByUsername(loginVO.getUsername());
        if (sysUser == null) {
            throw new VectorXException(ResultCodeEnum.FAIL.getCode(), "用户不存在");
        }
        if (!sysUser.getPassword()
                    .equals(MD5.encrypt(loginVO.getPassword()))) {
            throw new VectorXException(ResultCodeEnum.FAIL.getCode(), "密码错误");
        }
        if (sysUser.getStatus() == 0) {
            throw new VectorXException(ResultCodeEnum.FAIL.getCode(), "用户已被禁用");
        }

        Map<String, Object> param = new HashMap<>();
        param.put("token", JwtHelper.createToken(sysUser.getId(), sysUser.getUsername()));
        return Result.ok(param);
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/info")
    public Result<Object> info(HttpServletRequest request) {
        try {
            // 获取 JWT 令牌中的 username
            final String username = JwtHelper.getUsername(request.getHeader("token"));
            Map<String, Object> userInfo = sysUserService.getUserInfo(username);
            return Result.ok(userInfo);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Result.fail()
                         .message(e.getMessage());
        }
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.ok();
    }
}
