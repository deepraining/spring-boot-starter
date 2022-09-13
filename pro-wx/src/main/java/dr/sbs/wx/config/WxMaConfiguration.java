package dr.sbs.wx.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaRedisConfigImpl;
import javax.annotation.PostConstruct;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

@Configuration
public class WxMaConfiguration {
  @Value("${app.miniAppId}")
  private String miniAppId;

  @Value("${app.miniAppSecret}")
  private String miniAppSecret;

  @Value("${wxRedis.host}")
  private String wxRedisHost;

  @Value("${wxRedis.database}")
  private Integer wxRedisDatabase;

  @Value("${wxRedis.port}")
  private Integer wxRedisPort;

  @Value("${wxRedis.password}")
  private String wxRedisPassword;

  @Value("${wxRedis.timeout}")
  private Integer wxRedisTimeout;

  private static WxMaService wxMaService;

  @PostConstruct
  public void init() {
    WxMaRedisConfigImpl wxMaConfig =
        new WxMaRedisConfigImpl(
            new JedisPool(
                new GenericObjectPoolConfig(),
                wxRedisHost,
                wxRedisPort,
                wxRedisTimeout,
                wxRedisPassword,
                wxRedisDatabase));
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
