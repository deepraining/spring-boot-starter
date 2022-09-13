package dr.sbs.wx.service;

import dr.sbs.mbg.model.WxUser;

/** 用户管理Service */
public interface UserService {
  /** 通过 unionId 获取用户 */
  WxUser getByUnionId(String unionId);

  /** 生成 token */
  String generateToken(String code);

  /** 刷新 token */
  String refreshToken(String oldToken);

  /** 获取当前用户 */
  WxUser getCurrentUser();

  /** 获取当前用户unionId */
  String getCurrentUserUnionId();

  /** 获取当前用户Id */
  Long getCurrentUserId();

  /** 通过Id获取用户 */
  WxUser getById(Long id);

  /** 用户登陆 */
  String login(String code);

  /** 更新信息 */
  int update(String nickname, String avatar, Byte gender, String province, String city);

  /** 公众号登陆（snsapi_base） */
  String mpBaseLogin(String code);

  /** 公众号登陆（snsapi_userinfo） */
  String mpInfoLogin(String code);
}
