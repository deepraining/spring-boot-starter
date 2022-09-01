package dr.sbs.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import dr.sbs.admin.bo.AdminUserDetails;
import dr.sbs.admin.dao.AdminRoleDao;
import dr.sbs.admin.dao.AdminUserPermissionRelationDao;
import dr.sbs.admin.dao.AdminUserRoleRelationDao;
import dr.sbs.admin.dto.AdminUserParam;
import dr.sbs.admin.dto.UpdateAdminUserPasswordParam;
import dr.sbs.admin.service.AdminUserCacheService;
import dr.sbs.admin.service.AdminUserService;
import dr.sbs.admin.util.JwtTokenUtil;
import dr.sbs.mbg.mapper.AdminLoginLogMapper;
import dr.sbs.mbg.mapper.AdminUserMapper;
import dr.sbs.mbg.mapper.AdminUserPermissionRelationMapper;
import dr.sbs.mbg.mapper.AdminUserRoleRelationMapper;
import dr.sbs.mbg.model.AdminLoginLog;
import dr.sbs.mbg.model.AdminMenu;
import dr.sbs.mbg.model.AdminPermission;
import dr.sbs.mbg.model.AdminResource;
import dr.sbs.mbg.model.AdminRole;
import dr.sbs.mbg.model.AdminUser;
import dr.sbs.mbg.model.AdminUserExample;
import dr.sbs.mbg.model.AdminUserPermissionRelation;
import dr.sbs.mbg.model.AdminUserPermissionRelationExample;
import dr.sbs.mbg.model.AdminUserRoleRelation;
import dr.sbs.mbg.model.AdminUserRoleRelationExample;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/** AdminUserService实现类 */
@Service
public class AdminUserServiceImpl implements AdminUserService {
  private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserServiceImpl.class);
  @Autowired private JwtTokenUtil jwtTokenUtil;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private AdminUserMapper userMapper;
  @Autowired private AdminUserRoleRelationMapper roleRelationMapper;
  @Autowired private AdminUserRoleRelationDao roleRelationDao;
  @Autowired private AdminUserPermissionRelationMapper permissionRelationMapper;
  @Autowired private AdminUserPermissionRelationDao permissionRelationDao;
  @Autowired private AdminRoleDao roleDao;
  @Autowired private AdminLoginLogMapper loginLogMapper;
  @Autowired private AdminUserCacheService userCacheService;

  @Override
  public AdminUser getUserByUsername(String username) {
    AdminUser adminUser = userCacheService.getUser(username);
    if (adminUser != null) return adminUser;
    AdminUserExample example = new AdminUserExample();
    example.createCriteria().andUsernameEqualTo(username);
    List<AdminUser> adminList = userMapper.selectByExample(example);
    if (adminList != null && adminList.size() > 0) {
      adminUser = adminList.get(0);
      if (adminUser.getStatus() == 1) {
        userCacheService.setUser(adminUser);
        return adminUser;
      }
    }
    return null;
  }

  @Override
  public AdminUser register(AdminUserParam adminUserParam) {
    AdminUser adminUser = new AdminUser();
    BeanUtils.copyProperties(adminUserParam, adminUser);
    adminUser.setStatus(1);
    // 查询是否有相同用户名的用户
    AdminUserExample example = new AdminUserExample();
    example.createCriteria().andUsernameEqualTo(adminUser.getUsername());
    List<AdminUser> adminUserList = userMapper.selectByExample(example);
    if (adminUserList.size() > 0) {
      return null;
    }
    // 将密码进行加密操作
    String encodePassword = passwordEncoder.encode(adminUser.getPassword());
    adminUser.setPassword(encodePassword);
    userMapper.insertSelective(adminUser);
    return adminUser;
  }

  @Override
  public String login(String username, String password) {
    String token = null;
    // 密码需要客户端加密后传递
    try {
      UserDetails userDetails = loadUserByUsername(username);
      if (!passwordEncoder.matches(password, userDetails.getPassword())) {
        throw new BadCredentialsException("密码不正确");
      }
      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      token = jwtTokenUtil.generateToken(userDetails);
      updateLoginTimeByUsername(username);
      insertLoginLog(username);
    } catch (AuthenticationException e) {
      LOGGER.warn("登录异常:{}", e.getMessage());
    }
    return token;
  }

  /**
   * 添加登录记录
   *
   * @param username 用户名
   */
  private void insertLoginLog(String username) {
    AdminUser user = getUserByUsername(username);
    if (user == null) return;
    AdminLoginLog loginLog = new AdminLoginLog();
    loginLog.setUserId(user.getId());
    ServletRequestAttributes attributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    loginLog.setIp(request.getRemoteAddr());
    loginLogMapper.insertSelective(loginLog);
  }

  /** 根据用户名修改登录时间 */
  private void updateLoginTimeByUsername(String username) {
    AdminUser record = new AdminUser();
    record.setLastLoginTime(new Date());
    AdminUserExample example = new AdminUserExample();
    example.createCriteria().andUsernameEqualTo(username);
    userMapper.updateByExampleSelective(record, example);
  }

  @Override
  public String refreshToken(String oldToken) {
    return jwtTokenUtil.refreshHeadToken(oldToken);
  }

  @Override
  public AdminUser getItem(Long id) {
    return userMapper.selectByPrimaryKey(id);
  }

  @Override
  public List<AdminUser> list(String keyword, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    AdminUserExample example = new AdminUserExample();
    AdminUserExample.Criteria criteria = example.createCriteria();
    if (!StringUtils.isEmpty(keyword)) {
      criteria.andUsernameLike("%" + keyword + "%");
      example.or(example.createCriteria().andNicknameLike("%" + keyword + "%"));
    }
    return userMapper.selectByExample(example);
  }

  @Override
  public int update(Long id, AdminUser adminUser) {
    adminUser.setId(id);
    AdminUser rawUser = userMapper.selectByPrimaryKey(id);
    if (rawUser.getPassword().equals(adminUser.getPassword())) {
      // 与原加密密码相同的不需要修改
      adminUser.setPassword(null);
    } else {
      // 与原加密密码不同的需要加密修改
      if (StrUtil.isEmpty(adminUser.getPassword())) {
        adminUser.setPassword(null);
      } else {
        adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword()));
      }
    }
    // 账户名不能更改
    adminUser.setUsername(null);
    int count = userMapper.updateByPrimaryKeySelective(adminUser);
    userCacheService.delUser(id);
    return count;
  }

  @Override
  public int delete(Long id) {
    userCacheService.delUser(id);
    int count = userMapper.deleteByPrimaryKey(id);
    userCacheService.delResourceList(id);
    return count;
  }

  @Override
  public int updateRole(Long userId, List<Long> roleIds) {
    int count = roleIds == null ? 0 : roleIds.size();
    // 先删除原来的关系
    AdminUserRoleRelationExample adminRoleRelationExample = new AdminUserRoleRelationExample();
    adminRoleRelationExample.createCriteria().andUserIdEqualTo(userId);
    roleRelationMapper.deleteByExample(adminRoleRelationExample);
    // 建立新关系
    if (!CollectionUtils.isEmpty(roleIds)) {
      List<AdminUserRoleRelation> list = new ArrayList<>();
      for (Long roleId : roleIds) {
        AdminUserRoleRelation roleRelation = new AdminUserRoleRelation();
        roleRelation.setUserId(userId);
        roleRelation.setRoleId(roleId);
        list.add(roleRelation);
      }
      roleRelationDao.insertList(list);
    }
    userCacheService.delResourceList(userId);
    return count;
  }

  @Override
  public List<AdminRole> getRoleList(Long userId) {
    return roleRelationDao.getRoleList(userId);
  }

  @Override
  public List<AdminResource> getResourceList(Long userId) {
    List<AdminResource> resourceList = userCacheService.getResourceList(userId);
    if (CollUtil.isNotEmpty(resourceList)) {
      return resourceList;
    }
    resourceList = roleRelationDao.getResourceList(userId);
    if (CollUtil.isNotEmpty(resourceList)) {
      userCacheService.setResourceList(userId, resourceList);
    }
    return resourceList;
  }

  @Override
  public List<AdminMenu> getMenuList(Long userId) {
    return roleDao.getMenuListByUserId(userId);
  }

  @Override
  public int updatePermission(Long userId, List<Long> permissionIds) {
    // 删除原所有权限关系
    AdminUserPermissionRelationExample relationExample = new AdminUserPermissionRelationExample();
    relationExample.createCriteria().andUserIdEqualTo(userId);
    permissionRelationMapper.deleteByExample(relationExample);
    // 获取用户所有角色权限
    List<AdminPermission> permissionList = roleRelationDao.getRolePermissionList(userId);
    List<Long> rolePermissionList =
        permissionList.stream().map(AdminPermission::getId).collect(Collectors.toList());
    if (!CollectionUtils.isEmpty(permissionIds)) {
      List<AdminUserPermissionRelation> relationList = new ArrayList<>();
      // 筛选出+权限
      List<Long> addPermissionIdList =
          permissionIds.stream()
              .filter(permissionId -> !rolePermissionList.contains(permissionId))
              .collect(Collectors.toList());
      // 筛选出-权限
      List<Long> subPermissionIdList =
          rolePermissionList.stream()
              .filter(permissionId -> !permissionIds.contains(permissionId))
              .collect(Collectors.toList());
      // 插入+-权限关系
      relationList.addAll(convertPermissionRelation(userId, 1, addPermissionIdList));
      relationList.addAll(convertPermissionRelation(userId, -1, subPermissionIdList));
      return permissionRelationDao.insertList(relationList);
    }
    return 0;
  }

  /** 将+-权限关系转化为对象 */
  private List<AdminUserPermissionRelation> convertPermissionRelation(
      Long userId, Integer type, List<Long> permissionIdList) {
    List<AdminUserPermissionRelation> relationList =
        permissionIdList.stream()
            .map(
                permissionId -> {
                  AdminUserPermissionRelation relation = new AdminUserPermissionRelation();
                  relation.setUserId(userId);
                  relation.setType(type);
                  relation.setPermissionId(permissionId);
                  return relation;
                })
            .collect(Collectors.toList());
    return relationList;
  }

  @Override
  public List<AdminPermission> getPermissionList(Long userId) {
    return roleRelationDao.getPermissionList(userId);
  }

  @Override
  public int updatePassword(UpdateAdminUserPasswordParam param) {
    if (StrUtil.isEmpty(param.getUsername())
        || StrUtil.isEmpty(param.getOldPassword())
        || StrUtil.isEmpty(param.getNewPassword())) {
      return -1;
    }
    AdminUserExample example = new AdminUserExample();
    example.createCriteria().andUsernameEqualTo(param.getUsername());
    List<AdminUser> adminList = userMapper.selectByExample(example);
    if (CollUtil.isEmpty(adminList)) {
      return -2;
    }
    AdminUser adminUser = adminList.get(0);
    if (!passwordEncoder.matches(param.getOldPassword(), adminUser.getPassword())) {
      return -3;
    }
    adminUser.setPassword(passwordEncoder.encode(param.getNewPassword()));
    userMapper.updateByPrimaryKeySelective(adminUser);
    userCacheService.delUser(adminUser.getId());
    return 1;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    // 获取用户信息
    AdminUser adminUser = getUserByUsername(username);
    if (adminUser != null) {
      List<AdminResource> resourceList = getResourceList(adminUser.getId());
      return new AdminUserDetails(adminUser, resourceList);
    }
    throw new UsernameNotFoundException("用户名或密码错误");
  }
}
