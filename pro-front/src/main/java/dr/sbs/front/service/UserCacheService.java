package dr.sbs.front.service;

import dr.sbs.mbg.model.FrontUser;

/** 用户缓存操作类 */
public interface UserCacheService {
  /** 获取用户缓存 */
  FrontUser getUser(Long userId);

  /** 设置用户缓存 */
  void setUser(FrontUser frontUser);

  /** 删除用户缓存 */
  void delUser(Long userId);

  /** 获取用户缓存 */
  FrontUser getUserByUsername(String username);

  /** 设置用户缓存 */
  void setUserByUsername(FrontUser frontUser);

  /** 删除用户缓存 */
  void delUserByUsername(String username);
}
