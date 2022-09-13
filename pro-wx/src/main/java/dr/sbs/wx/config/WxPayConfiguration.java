package dr.sbs.wx.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxPayConfiguration {
  @Value("${app.miniAppId}")
  private String miniAppId;

  @Value("${app.mpAppId}")
  private String mpAppId;

  @Value("${wxPay.mchId}")
  private String wxPayMchId;

  @Value("${wxPay.mchKey}")
  private String wxPayMchKey;

  @Value("${wxPay.keyPath}")
  private String wxPayKeyPath;

  @Value("${wxPay.notifyUrl}")
  private String wxPayNotifyUrl;

  private static WxPayService miniWxPayService;
  private static WxPayService mpWxPayService;

  @PostConstruct
  public void init() {
    WxPayConfig miniPayConfig = new WxPayConfig();
    miniPayConfig.setAppId(StringUtils.trimToNull(miniAppId));
    miniPayConfig.setMchId(StringUtils.trimToNull(wxPayMchId));
    miniPayConfig.setMchKey(StringUtils.trimToNull(wxPayMchKey));
    miniPayConfig.setKeyPath(StringUtils.trimToNull(wxPayKeyPath));
    miniPayConfig.setNotifyUrl(StringUtils.trimToNull(wxPayNotifyUrl));
    miniPayConfig.setTradeType("JSAPI");

    // 可以指定是否使用沙箱环境
    miniPayConfig.setUseSandboxEnv(false);

    miniWxPayService = new WxPayServiceImpl();
    miniWxPayService.setConfig(miniPayConfig);

    WxPayConfig mpPayConfig = new WxPayConfig();
    mpPayConfig.setAppId(StringUtils.trimToNull(mpAppId));
    mpPayConfig.setMchId(StringUtils.trimToNull(wxPayMchId));
    mpPayConfig.setMchKey(StringUtils.trimToNull(wxPayMchKey));
    mpPayConfig.setKeyPath(StringUtils.trimToNull(wxPayKeyPath));
    mpPayConfig.setNotifyUrl(StringUtils.trimToNull(wxPayNotifyUrl));
    mpPayConfig.setTradeType("JSAPI");

    // 可以指定是否使用沙箱环境
    mpPayConfig.setUseSandboxEnv(false);

    mpWxPayService = new WxPayServiceImpl();
    mpWxPayService.setConfig(mpPayConfig);
  }

  public static WxPayService getMiniWxPayService() {
    return miniWxPayService;
  }

  public static WxPayService getMpWxPayService() {
    return mpWxPayService;
  }
}
