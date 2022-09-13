package dr.sbs.wx.service;

import dr.sbs.mbg.model.WxUser;

/** 用户缓存操作类 */
public interface UserCacheService {
  /** 获取缓存用户信息 */
  WxUser getUser(String unionId);

  /** 设置缓存用户信息 */
  void setUser(WxUser wxUser);

  /** 删除缓存用户信息 */
  void delUser(String unionId);

  /** 通过Id获取缓存用户信息 */
  WxUser getUserById(Long id);

  /** 通过Id设置缓存用户信息 */
  void setUserById(WxUser wxUser);

  /** 通过Id删除缓存用户信息 */
  void delUserById(Long id);
}
