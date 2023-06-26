package dr.sbs.wx.config;

import javax.annotation.PostConstruct;
import me.chanjar.weixin.common.redis.RedisTemplateWxRedisOps;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpRedisConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class WxMpConfiguration {
  @Value("${app.mpAppId}")
  private String mpAppId;

  @Value("${app.mpAppSecret}")
  private String mpAppSecret;

  @Qualifier("wxStringRedisTemplate")
  @Autowired
  StringRedisTemplate wxStringRedisTemplate;

  private static WxMpService wxMpService;

  @PostConstruct
  public void init() {
    RedisTemplateWxRedisOps redisTemplateWxRedisOps =
        new RedisTemplateWxRedisOps(wxStringRedisTemplate);

    WxMpRedisConfigImpl wxMpConfig = new WxMpRedisConfigImpl(redisTemplateWxRedisOps, "wp");
    wxMpConfig.setAppId(mpAppId);
    wxMpConfig.setSecret(mpAppSecret);

    wxMpService = new WxMpServiceImpl();
    wxMpService.setWxMpConfigStorage(wxMpConfig);
  }

  public static WxMpService getWxMpService() {
    return wxMpService;
  }
}
