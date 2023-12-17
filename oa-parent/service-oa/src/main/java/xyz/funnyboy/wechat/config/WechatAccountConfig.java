package xyz.funnyboy.wechat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author VectorX
 * @version V1.0
 * @description 微信公众账号配置类
 * @date 17/12/2023
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig
{
    private String mpAppId;

    private String mpAppSecret;
}
