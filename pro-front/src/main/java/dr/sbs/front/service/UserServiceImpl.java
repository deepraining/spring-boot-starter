package dr.sbs.front.service;

import dr.sbs.common.exception.ApiAssert;
import dr.sbs.common.util.UuidUtil;
import dr.sbs.front.bo.UserInfo;
import dr.sbs.front.dto.UpdatePasswordParam;
import dr.sbs.front.dto.UserCreateParam;
import dr.sbs.mbg.mapper.FrontUserMapper;
import dr.sbs.mbg.model.FrontUser;
import dr.sbs.mbg.model.FrontUserExample;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  @Autowired private FrontUserMapper userMapper;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private UserCacheService userCacheService;

  @Override
  public FrontUser getByUsername(String username) {
    FrontUser user = userCacheService.getUserByUsername(username);
    if (user != null) return user;

    FrontUserExample example = new FrontUserExample();
    example.createCriteria().andUsernameEqualTo(username);
    List<FrontUser> users = userMapper.selectByExample(example);
    if (users.size() > 0) {
      user = users.get(0);
      userCacheService.setUser(user);
      userCacheService.setUserByUsername(user);
      return user;
    }
    return null;
  }

  @Override
  public FrontUser getById(Long id) {
    FrontUser user = userCacheService.getUser(id);
    if (user != null) return user;

    user = userMapper.selectByPrimaryKey(id);
    if (user != null) {
      userCacheService.setUser(user);
      userCacheService.setUserByUsername(user);
      return user;
    }
    return null;
  }

  @Override
  public FrontUser register(UserCreateParam userCreateParam) {
    // check if the username existed
    String username = userCreateParam.getUsername();
    FrontUserExample example = new FrontUserExample();
    example.createCriteria().andUsernameEqualTo(username);
    List<FrontUser> users = userMapper.selectByExample(example);
    if (users.size() > 0) {
      ApiAssert.fail("Username '" + username + "' existed.");
    }

    // Add the user
    FrontUser user = new FrontUser();
    BeanUtils.copyProperties(userCreateParam, user);
    user.setId(UuidUtil.nextId());
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(userCreateParam.getPassword()));
    userMapper.insertSelective(user);
    return user;
  }

  @Override
  public Integer updatePassword(UpdatePasswordParam updatePasswordParam) {
    FrontUser user = getCurrentUser();
    if (user == null) ApiAssert.fail("未登录");

    if (!passwordEncoder.matches(updatePasswordParam.getOldPassword(), user.getPassword())) {
      ApiAssert.fail("原密码错误");
    }

    FrontUser updateUser = new FrontUser();
    updateUser.setId(user.getId());
    updateUser.setPassword(passwordEncoder.encode(updatePasswordParam.getNewPassword()));
    int count = userMapper.updateByPrimaryKeySelective(updateUser);

    userCacheService.delUser(user.getId());
    userCacheService.delUserByUsername(user.getUsername());

    return count;
  }

  @Override
  public FrontUser getCurrentUser() {
    SecurityContext ctx = SecurityContextHolder.getContext();
    Authentication auth = ctx.getAuthentication();

    if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
      return null;
    }

    UserInfo userInfo = (UserInfo) auth.getPrincipal();
    return userInfo.getUser();
  }
}
