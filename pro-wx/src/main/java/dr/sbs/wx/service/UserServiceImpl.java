package dr.sbs.wx.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import dr.sbs.common.exception.ApiAssert;
import dr.sbs.common.util.UuidUtil;
import dr.sbs.mbg.mapper.WxUserMapper;
import dr.sbs.mbg.model.WxUser;
import dr.sbs.mbg.model.WxUserExample;
import dr.sbs.wx.bo.UserInfo;
import dr.sbs.wx.config.WxMaConfiguration;
import dr.sbs.wx.config.WxMpConfiguration;
import dr.sbs.wx.util.JwtTokenUtil;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
  @Autowired private UserDetailsService userDetailsService;
  @Autowired private UserCacheService userCacheService;
  @Autowired private JwtTokenUtil jwtTokenUtil;
  @Autowired private WxUserMapper wxUserMapper;
  @Autowired private UploadService uploadService;

  @Value("${jwt.tokenHead}")
  private String tokenHead;

  @Override
  public WxUser getByUnionId(String unionId) {
    WxUser wxUser = userCacheService.getUser(unionId);
    if (wxUser != null) return wxUser;

    WxUserExample example = new WxUserExample();
    example.createCriteria().andStatusEqualTo((byte) 1).andUnionIdEqualTo(unionId);
    List<WxUser> users = wxUserMapper.selectByExample(example);
    if (!CollectionUtils.isEmpty(users)) {
      wxUser = users.get(0);
      userCacheService.setUser(wxUser);
      userCacheService.setUserById(wxUser);
      return wxUser;
    }
    return null;
  }

  @Override
  public String generateToken(String unionId) {
    String token = null;
    try {
      UserDetails userDetails = userDetailsService.loadUserByUsername(unionId);
      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      token = jwtTokenUtil.generateToken(userDetails);
    } catch (AuthenticationException e) {
      log.warn("Login failed: {}", e.getMessage());
    }
    return token;
  }

  @Override
  public String refreshToken(String oldToken) {
    String token = oldToken.substring(tokenHead.length());
    return jwtTokenUtil.refreshHeadToken(token);
  }

  @Override
  public WxUser getCurrentUser() {
    SecurityContext ctx = SecurityContextHolder.getContext();
    Authentication auth = ctx.getAuthentication();

    if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
      return null;
    }

    UserInfo userInfo = (UserInfo) auth.getPrincipal();
    return userInfo.getUser();
  }

  @Override
  public String getCurrentUserUnionId() {
    WxUser wxUser = getCurrentUser();

    if (wxUser == null) return null;
    return wxUser.getUnionId();
  }

  @Override
  public Long getCurrentUserId() {
    WxUser currentUser = getCurrentUser();

    if (currentUser == null) return null;
    return currentUser.getId();
  }

  @Override
  public WxUser getById(Long id) {
    WxUser user = userCacheService.getUserById(id);
    if (user != null) return user;

    user = wxUserMapper.selectByPrimaryKey(id);
    if (user != null) {
      userCacheService.setUser(user);
      userCacheService.setUserById(user);
      return user;
    }
    return null;
  }

  @Override
  public String login(String code) {
    WxMaService wxMaService = WxMaConfiguration.getWxMaService();

    // auth.code2Session
    WxMaJscode2SessionResult result = null;
    try {
      result = wxMaService.jsCode2SessionInfo(code);
    } catch (WxErrorException e) {
      log.error("微信授权 auth.code2Session 发生错误: {}！", e.getMessage());
      ApiAssert.fail("微信授权失败");
    }

    String openId = result.getOpenid();
    String unionId = result.getUnionid();

    WxUser user = getByUnionId(unionId);

    Date now = new Date();
    Long id;

    // 组装用户信息
    WxUser newUser = new WxUser();
    newUser.setMiniOpenId(openId);
    newUser.setLastLogin(now);

    // 没有注册
    if (user == null) {
      id = UuidUtil.nextId();
      newUser.setId(id);
      newUser.setUnionId(unionId);
      newUser.setRegisterDate(now);
      wxUserMapper.insertSelective(newUser);
    } else if (StringUtils.isEmpty(user.getMiniOpenId())) {
      // 公众号注册过
      id = user.getId();
      newUser.setId(id);
      wxUserMapper.updateByPrimaryKeySelective(newUser);
      userCacheService.delUser(unionId);
      userCacheService.delUserById(id);
    }

    return generateToken(unionId);
  }

  @Override
  public int update(String nickname, String avatar, Byte gender, String province, String city) {
    WxUser currentUser = getCurrentUser();

    if (currentUser == null) return -1;

    WxUser updateUser = new WxUser();
    updateUser.setId(currentUser.getId());
    if (!StringUtils.isEmpty(nickname)) updateUser.setNickname(nickname);
    if (gender != null) updateUser.setGender(gender);
    if (!StringUtils.isEmpty(province)) updateUser.setProvince(province);
    if (!StringUtils.isEmpty(city)) updateUser.setCity(city);

    if (!StringUtils.isEmpty(avatar)) {
      updateUser.setAvatar(avatar);
      try {
        String savedUrl = uploadService.uploadUrl(avatar);
        updateUser.setAvatar(savedUrl);
      } catch (Exception e) {
        log.error("上传发生错误: {}！", e.getMessage());
      }
    }

    wxUserMapper.updateByPrimaryKeySelective(updateUser);
    userCacheService.delUser(currentUser.getUnionId());
    userCacheService.delUserById(currentUser.getId());

    return 1;
  }

  private WxUser getByMpOpenId(String openId) {
    WxUserExample example = new WxUserExample();
    example.createCriteria().andMpOpenIdEqualTo(openId);
    List<WxUser> users = wxUserMapper.selectByExample(example);

    // 没有注册
    if (users == null || users.size() < 1) return null;
    return users.get(0);
  }

  @Override
  public String mpBaseLogin(String code) {
    WxMpService wxMpService = WxMpConfiguration.getWxMpService();

    // 通过code换取网页授权access_token
    WxMpOAuth2AccessToken result = null;
    try {
      result = wxMpService.getOAuth2Service().getAccessToken(code);
    } catch (WxErrorException e) {
      log.error("通过code换取网页授权access_token发生错误: {}！", e.getMessage());
      ApiAssert.fail("微信授权失败");
    }

    WxUser user = getByMpOpenId(result.getOpenId());

    // 没有注册
    if (user == null) return null;

    // 更新用户的最后登陆时间
    WxUser updateUser = new WxUser();
    updateUser.setId(user.getId());
    updateUser.setLastLogin(new Date());
    wxUserMapper.updateByPrimaryKeySelective(updateUser);

    return generateToken(user.getUnionId());
  }

  @Override
  public String mpInfoLogin(String code) {
    WxMpService wxMpService = WxMpConfiguration.getWxMpService();

    // 通过code换取网页授权access_token
    WxMpOAuth2AccessToken result = null;
    try {
      result = wxMpService.getOAuth2Service().getAccessToken(code);
    } catch (WxErrorException e) {
      log.error("通过code换取网页授权access_token发生错误: {}！", e.getMessage());
      ApiAssert.fail("微信授权失败");
    }

    WxUser user = getByMpOpenId(result.getOpenId());

    // 已注册
    if (user != null) return generateToken(user.getUnionId());

    // 获取用户信息
    WxMpUser userInfo = null;
    try {
      userInfo = wxMpService.getOAuth2Service().getUserInfo(result, "zh_CN");
    } catch (WxErrorException e) {
      log.error("通过access_token拉取用户信息发生错误: {}！", e.getMessage());
      ApiAssert.fail("微信授权失败");
    }

    // 不为null，则是在小程序已注册
    user = getByUnionId(userInfo.getUnionId());

    Date now = new Date();
    Long id;
    String unionId;

    // 组装用户信息
    WxUser wxUser = new WxUser();
    wxUser.setMpOpenId(userInfo.getOpenId());
    // 新用户
    if (user == null) {
      id = UuidUtil.nextId();
      unionId = userInfo.getUnionId();
      wxUser.setId(id);
      wxUser.setUnionId(userInfo.getUnionId());
      wxUser.setNickname(userInfo.getNickname());
      wxUser.setAvatar(userInfo.getHeadImgUrl());
      wxUser.setGender(userInfo.getSex().byteValue());
      wxUser.setProvince(userInfo.getProvince());
      wxUser.setCity(userInfo.getCity());
      wxUser.setRegisterDate(now);
      wxUser.setLastLogin(now);

      try {
        String savedUrl = uploadService.uploadUrl(userInfo.getHeadImgUrl());
        wxUser.setAvatar(savedUrl);
      } catch (Exception e) {
        log.error("上传发生错误: {}！", e.getMessage());
      }
    } else {
      id = user.getId();
      unionId = user.getUnionId();
      wxUser.setLastLogin(now);
      wxUser.setId(id);
    }

    // 更新
    if (user != null) {
      wxUserMapper.updateByPrimaryKeySelective(wxUser);
      userCacheService.delUser(unionId);
      userCacheService.delUserById(id);
    }
    // 添加
    else {
      wxUserMapper.insertSelective(wxUser);
    }

    return generateToken(unionId);
  }
}
