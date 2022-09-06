package dr.sbs.front.service;

import dr.sbs.common.util.SbsCacheKey;
import dr.sbs.common.util.SbsCacheKeyUtil;
import dr.sbs.mbg.model.FrontUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCacheServiceImpl implements UserCacheService {
  @Autowired private UserService userService;
  @Autowired private RedisService redisService;

  @Override
  public FrontUser getUser(Long userId) {
    String cacheKey = SbsCacheKeyUtil.getFrontDbKey(SbsCacheKey.FRONT_USER, userId);
    return (FrontUser) redisService.get(cacheKey);
  }

  @Override
  public void setUser(FrontUser frontUser) {
    String cacheKey = SbsCacheKeyUtil.getFrontDbKey(SbsCacheKey.FRONT_USER, frontUser.getId());
    redisService.set(cacheKey, frontUser, SbsCacheKeyUtil.TWO_WEEKS);
  }

  @Override
  public void delUser(Long userId) {
    String cacheKey = SbsCacheKeyUtil.getFrontDbKey(SbsCacheKey.FRONT_USER, userId);
    redisService.del(cacheKey);
  }

  @Override
  public FrontUser getUserByUsername(String username) {
    String cacheKey = SbsCacheKeyUtil.getFrontDbKey(SbsCacheKey.FRONT_USER, username);
    return (FrontUser) redisService.get(cacheKey);
  }

  @Override
  public void setUserByUsername(FrontUser frontUser) {
    String cacheKey =
        SbsCacheKeyUtil.getFrontDbKey(SbsCacheKey.FRONT_USER, frontUser.getUsername());
    redisService.set(cacheKey, frontUser, SbsCacheKeyUtil.TWO_WEEKS);
  }

  @Override
  public void delUserByUsername(String username) {
    String cacheKey = SbsCacheKeyUtil.getFrontDbKey(SbsCacheKey.FRONT_USER, username);
    redisService.del(cacheKey);
  }
}
