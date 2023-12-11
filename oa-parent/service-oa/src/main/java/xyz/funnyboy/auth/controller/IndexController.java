package xyz.funnyboy.auth.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.funnyboy.common.result.Result;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "后台登录管理")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController
{
    @PostMapping("/login")
    public Result<Map<String, Object>> login() {
        Map<String, Object> param = new HashMap<>();
        param.put("token", "admin");
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
