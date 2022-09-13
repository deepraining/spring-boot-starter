package dr.sbs.wx.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import dr.sbs.common.exception.ApiAssert;
import dr.sbs.wx.config.WxMaConfiguration;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Service;

/** MiscService实现类 */
@Service
@Slf4j
public class MiscServiceImpl implements MiscService {
  @Override
  public String getWxPhone(String code, String encryptedData, String iv) {
    // 每次更注册前，都用wx.login获取新code，用来获取最新的sessionKey（sessionKey有效期是3天）
    WxMaService wxMaService = WxMaConfiguration.getWxMaService();

    // auth.code2Session
    WxMaJscode2SessionResult result = null;
    try {
      result = wxMaService.jsCode2SessionInfo(code);
    } catch (WxErrorException e) {
      log.error("微信授权 auth.code2Session 发生错误: {}！", e.getMessage());
      ApiAssert.fail("微信授权失败");
    }

    String sessionKey = result.getSessionKey();

    WxMaPhoneNumberInfo phoneNoInfo =
        wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

    return phoneNoInfo.getPurePhoneNumber();
  }
}
