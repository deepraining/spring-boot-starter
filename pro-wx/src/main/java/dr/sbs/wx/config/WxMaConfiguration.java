package dr.sbs.wx.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaRedisBetterConfigImpl;
import javax.annotation.PostConstruct;
import me.chanjar.weixin.common.redis.RedisTemplateWxRedisOps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class WxMaConfiguration {
  @Value("${app.miniAppId}")
  private String miniAppId;

  @Value("${app.miniAppSecret}")
  private String miniAppSecret;

  @Qualifier("wxStringRedisTemplate")
  @Autowired
  StringRedisTemplate wxStringRedisTemplate;

  private static WxMaService wxMaService;

  @PostConstruct
  public void init() {
    RedisTemplateWxRedisOps redisTemplateWxRedisOps =
        new RedisTemplateWxRedisOps(wxStringRedisTemplate);

    WxMaRedisBetterConfigImpl wxMaConfig =
        new WxMaRedisBetterConfigImpl(redisTemplateWxRedisOps, "wa");
    wxMaConfig.setAppid(miniAppId);
    wxMaConfig.setSecret(miniAppSecret);
    wxMaConfig.setMsgDataFormat("JSON");

    wxMaService = new WxMaServiceImpl();
    wxMaService.setWxMaConfig(wxMaConfig);
  }

  public static WxMaService getWxMaService() {
    return wxMaService;
  }
}
