package dr.sbs.wx.service;

/** 杂项操作类 */
public interface MiscService {
  /** 获取微信手机号 */
  String getWxPhone(String code, String encryptedData, String iv);
}
