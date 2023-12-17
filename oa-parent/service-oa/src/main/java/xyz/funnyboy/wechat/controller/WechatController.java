package xyz.funnyboy.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.auth.service.SysUserService;
import xyz.funnyboy.common.jwt.JwtHelper;
import xyz.funnyboy.common.result.Result;
import xyz.funnyboy.model.system.SysUser;
import xyz.funnyboy.vo.wechat.BindPhoneVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

/**
 * @author VectorX
 * @version V1.0
 * @description 微信授权登录
 * @date 17/12/2023
 */
@Api(tags = "微信授权登录")
@Controller
@RequestMapping("/admin/wechat")
@Slf4j
@CrossOrigin
public class WechatController
{
    @Resource
    private SysUserService sysUserService;

    @Autowired
    private WxMpService wxMpService;

    @Value("${wechat.backUrl}")
    private String backUrl;

    @ApiOperation(value = "微信网页授权-authorize")
    @GetMapping("/authorize")
    public String authorize(
            @RequestParam("returnUrl")
                    String returnUrl, HttpServletRequest request) {
        log.info("【微信网页授权】returnUrl={}", returnUrl);
        //由于授权回调成功后，要返回原地址路径，原地址路径带“#”号，当前returnUrl获取带“#”的url获取不全，因此前端把“#”号替换为“guiguoa”了，这里要还原一下
        final String encode = URLEncoder.encode(returnUrl.replace("vectorx", "#"));
        String redirectURL = wxMpService.getOAuth2Service()
                                        .buildAuthorizationUrl(backUrl, WxConsts.OAuth2Scope.SNSAPI_USERINFO, encode);
        log.info("【微信网页授权】获取code,redirectURL={}", redirectURL);
        return "redirect:" + redirectURL;
    }

    @ApiOperation(value = "微信网页授权-userInfo")
    @GetMapping("/userInfo")
    public String userInfo(
            @RequestParam("code")
                    String code,
            @RequestParam("state")
                    String returnUrl) throws Exception {
        log.info("【微信网页授权】code={}", code);
        log.info("【微信网页授权】state={}", returnUrl);
        WxOAuth2AccessToken accessToken = wxMpService.getOAuth2Service()
                                                     .getAccessToken(code);
        String openId = accessToken.getOpenId();
        log.info("【微信网页授权】openId={}", openId);

        WxOAuth2UserInfo wxMpUser = wxMpService.getOAuth2Service()
                                               .getUserInfo(accessToken, null);
        log.info("【微信网页授权】wxMpUser={}", JSON.toJSONString(wxMpUser));

        // 查询用户信息
        SysUser sysUser = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getOpenId, openId));

        String token = "";
        //null != sysUser 说明已经绑定，反之为建立账号绑定，去页面建立账号绑定
        if (null != sysUser) {
            token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());
        }
        if (!returnUrl.contains("?")) {
            final String redirectUrl = returnUrl + "?token=" + token + "&openId=" + openId;
            log.info("【微信网页授权】redirectUrl={}", redirectUrl);
            return "redirect:" + redirectUrl;
        }
        else {
            final String redirectUrl = returnUrl + "&token=" + token + "&openId=" + openId;
            log.info("【微信网页授权】redirectUrl={}", redirectUrl);
            return "redirect:" + redirectUrl;
        }
    }

    @ApiOperation(value = "微信账号绑定手机")
    @PostMapping("bindPhone")
    @ResponseBody
    public Result<Object> bindPhone(
            @RequestBody
                    BindPhoneVO bindPhoneVO) {
        // 查询用户信息
        SysUser sysUser = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhone, bindPhoneVO.getPhone()));
        if (null == sysUser) {
            return Result.fail()
                         .message("手机号码不存在，绑定失败");
        }

        // 更新用户的openId
        sysUser.setOpenId(bindPhoneVO.getOpenId());
        sysUserService.updateById(sysUser);

        // 返回token
        String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());
        return Result.ok(token);
    }
}
