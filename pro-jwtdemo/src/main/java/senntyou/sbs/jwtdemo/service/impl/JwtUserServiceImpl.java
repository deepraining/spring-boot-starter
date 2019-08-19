package senntyou.sbs.jwtdemo.service.impl;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import senntyou.sbs.common.CommonResult;
import senntyou.sbs.jwtdemo.bo.UserInfo;
import senntyou.sbs.jwtdemo.component.JwtToken;
import senntyou.sbs.jwtdemo.service.JwtUserService;
import senntyou.sbs.mbg.mapper.JwtUserMapper;
import senntyou.sbs.mbg.model.JwtUser;
import senntyou.sbs.mbg.model.JwtUserExample;

@Service
@Slf4j
public class JwtUserServiceImpl implements JwtUserService {
  @Autowired private UserDetailsService userDetailsService;
  @Autowired private JwtUserMapper jwtUserMapper;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private JwtToken jwtToken;

  @Value("${jwt.tokenHead}")
  private String tokenHead;

  @Override
  public JwtUser getByUsername(String username) {
    JwtUserExample example = new JwtUserExample();
    example.createCriteria().andUsernameEqualTo(username);
    List<JwtUser> users = jwtUserMapper.selectByExample(example);
    if (!CollectionUtils.isEmpty(users)) {
      return users.get(0);
    }
    return null;
  }

  @Override
  public JwtUser getById(long id) {
    JwtUserExample example = new JwtUserExample();
    example.createCriteria().andIdEqualTo(id);
    List<JwtUser> users = jwtUserMapper.selectByExample(example);
    if (!CollectionUtils.isEmpty(users)) {
      return users.get(0);
    }
    return null;
  }

  @Override
  public CommonResult register(String username, String password) {
    // check if the username existed
    JwtUserExample example = new JwtUserExample();
    example.createCriteria().andUsernameEqualTo(username);
    List<JwtUser> users = jwtUserMapper.selectByExample(example);
    if (!CollectionUtils.isEmpty(users)) {
      return CommonResult.failed("User '" + username + "' existed");
    }

    // Add the user
    JwtUser user = new JwtUser();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(password));
    jwtUserMapper.insertSelective(user);
    return CommonResult.success(null, "Sign up succeeded");
  }

  @Override
  public String login(String username, String password) {
    String token = null;

    try {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      if (!passwordEncoder.matches(password, userDetails.getPassword())) {
        throw new BadCredentialsException("Password is not correct.");
      }
      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      token = jwtToken.generateToken(userDetails);
    } catch (AuthenticationException e) {
      log.warn("Login failed: {}", e.getMessage());
    }
    return token;
  }

  @Override
  public String refreshToken(String oldToken) {
    String token = oldToken.substring(tokenHead.length());
    if (jwtToken.canRefresh(token)) {
      return jwtToken.refreshToken(token);
    }
    return null;
  }

  @Override
  public CommonResult updatePassword(String username, String password) {
    JwtUserExample example = new JwtUserExample();
    example.createCriteria().andUsernameEqualTo(username);
    List<JwtUser> users = jwtUserMapper.selectByExample(example);
    if (CollectionUtils.isEmpty(users)) {
      return CommonResult.failed("Account not existed");
    }
    JwtUser user = users.get(0);
    user.setPassword(passwordEncoder.encode(password));
    jwtUserMapper.updateByPrimaryKeySelective(user);
    return CommonResult.success(null, "Update password succeeded");
  }

  @Override
  public JwtUser getCurrentUser() {
    SecurityContext ctx = SecurityContextHolder.getContext();
    Authentication auth = ctx.getAuthentication();

    if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
      return null;
    }

    UserInfo userInfo = (UserInfo) auth.getPrincipal();
    return userInfo.getUser();
  }
}
