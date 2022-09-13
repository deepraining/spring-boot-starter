package dr.sbs.wx.service;

import dr.sbs.common.util.SbsCacheKey;
import dr.sbs.common.util.SbsCacheKeyUtil;
import dr.sbs.mbg.model.WxUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** UserCacheService实现类 */
@Service
public class UserCacheServiceImpl implements UserCacheService {
  @Autowired private RedisService redisService;

  @Override
  public WxUser getUser(String unionId) {
    String cacheKey = SbsCacheKeyUtil.getFrontDbKey(SbsCacheKey.FRONT_WX_USER, unionId);
    return (WxUser) redisService.get(cacheKey);
  }

  @Override
  public void setUser(WxUser wxUser) {
    String cacheKey = SbsCacheKeyUtil.getFrontDbKey(SbsCacheKey.FRONT_WX_USER, wxUser.getUnionId());
    // 缓存14天
    redisService.set(cacheKey, wxUser, SbsCacheKeyUtil.TWO_WEEKS);
  }

  @Override
  public void delUser(String unionId) {
    String cacheKey = SbsCacheKeyUtil.getFrontDbKey(SbsCacheKey.FRONT_WX_USER, unionId);
    redisService.del(cacheKey);
  }

  @Override
  public WxUser getUserById(Long id) {
    String cacheKey = SbsCacheKeyUtil.getFrontDbKey(SbsCacheKey.FRONT_WX_USER_BY_ID, id);
    return (WxUser) redisService.get(cacheKey);
  }

  @Override
  public void setUserById(WxUser wxUser) {
    String cacheKey =
        SbsCacheKeyUtil.getFrontDbKey(SbsCacheKey.FRONT_WX_USER_BY_ID, wxUser.getId());
    // 缓存14天
    redisService.set(cacheKey, wxUser, SbsCacheKeyUtil.TWO_WEEKS);
  }

  @Override
  public void delUserById(Long id) {
    String cacheKey = SbsCacheKeyUtil.getFrontDbKey(SbsCacheKey.FRONT_WX_USER_BY_ID, id);
    redisService.del(cacheKey);
  }
}
