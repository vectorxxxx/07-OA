package xyz.funnyboy.auth.controller;

import io.swagger.annotations.Api;
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

import java.util.HashMap;
import java.util.Map;

@Api(tags = "后台登录管理")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController
{
    @Autowired
    private SysUserService sysUserService;

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

    @GetMapping("/info")
    public Result<Map<String, Object>> info() {
        Map<String, Object> param = new HashMap<>();
        param.put("roles", "[admin]");
        param.put("name", "admin");
        param.put("avatar", "https://crowdfunding-vectorx.oss-cn-shanghai.aliyuncs.com/20231210/1.png");
        return Result.ok(param);
    }

    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.ok();
    }
}
