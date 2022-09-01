package dr.sbs.demo.service.impl;

import dr.sbs.common.CommonResult;
import dr.sbs.common.util.UuidUtil;
import dr.sbs.demo.bo.UserInfo;
import dr.sbs.demo.service.UserService;
import dr.sbs.mbg.mapper.UserMapper;
import dr.sbs.mbg.model.User;
import dr.sbs.mbg.model.UserExample;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class UserServiceImpl implements UserService {
  @Autowired private UserMapper userMapper;
  @Autowired private PasswordEncoder passwordEncoder;

  @Override
  public User getByUsername(String username) {
    UserExample example = new UserExample();
    example.createCriteria().andUsernameEqualTo(username);
    List<User> users = userMapper.selectByExample(example);
    if (!CollectionUtils.isEmpty(users)) {
      return users.get(0);
    }
    return null;
  }

  @Override
  public User getById(long id) {
    UserExample example = new UserExample();
    example.createCriteria().andIdEqualTo(id);
    List<User> users = userMapper.selectByExample(example);
    if (!CollectionUtils.isEmpty(users)) {
      return users.get(0);
    }
    return null;
  }

  @Override
  public CommonResult register(String username, String password, String email) {
    // check if the username existed
    UserExample example = new UserExample();
    example.createCriteria().andUsernameEqualTo(username);
    example.or(example.createCriteria().andEmailEqualTo(email));
    List<User> users = userMapper.selectByExample(example);
    if (!CollectionUtils.isEmpty(users)) {
      return CommonResult.failed("User '" + username + "' existed");
    }

    // Add the user
    User user = new User();
    user.setId(UuidUtil.nextId());
    user.setUsername(username);
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(password));
    userMapper.insertSelective(user);
    return CommonResult.success(null, "Sign up succeeded");
  }

  @Override
  public CommonResult updatePassword(String username, String password) {
    UserExample example = new UserExample();
    example.createCriteria().andUsernameEqualTo(username);
    List<User> users = userMapper.selectByExample(example);
    if (CollectionUtils.isEmpty(users)) {
      return CommonResult.failed("Account not existed");
    }
    User user = users.get(0);
    user.setPassword(passwordEncoder.encode(password));
    userMapper.updateByPrimaryKeySelective(user);
    return CommonResult.success(null, "Update password succeeded");
  }

  @Override
  public User getCurrentUser() {
    SecurityContext ctx = SecurityContextHolder.getContext();
    Authentication auth = ctx.getAuthentication();

    if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
      return null;
    }

    UserInfo userInfo = (UserInfo) auth.getPrincipal();
    return userInfo.getUser();
  }
}
