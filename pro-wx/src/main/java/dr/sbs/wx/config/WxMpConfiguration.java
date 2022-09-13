package dr.sbs.wx.config;

import javax.annotation.PostConstruct;
import me.chanjar.weixin.common.redis.JedisWxRedisOps;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpRedisConfigImpl;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

@Configuration
public class WxMpConfiguration {
  @Value("${app.mpAppId}")
  private String mpAppId;

  @Value("${app.mpAppSecret}")
  private String mpAppSecret;

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

  private static WxMpService wxMpService;

  @PostConstruct
  public void init() {
    JedisPool jedisPool =
        new JedisPool(
            new GenericObjectPoolConfig(),
            wxRedisHost,
            wxRedisPort,
            wxRedisTimeout,
            wxRedisPassword,
            wxRedisDatabase);
    JedisWxRedisOps jedisWxRedisOps = new JedisWxRedisOps(jedisPool);

    WxMpRedisConfigImpl wxMpConfig = new WxMpRedisConfigImpl(jedisWxRedisOps, mpAppId);
    wxMpConfig.setAppId(mpAppId);
    wxMpConfig.setSecret(mpAppSecret);

    wxMpService = new WxMpServiceImpl();
    wxMpService.setWxMpConfigStorage(wxMpConfig);
  }

  public static WxMpService getWxMpService() {
    return wxMpService;
  }
}
