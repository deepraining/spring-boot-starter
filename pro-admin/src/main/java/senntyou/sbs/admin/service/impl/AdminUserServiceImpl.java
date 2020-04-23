package senntyou.sbs.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
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
import senntyou.sbs.admin.bo.AdminUserDetails;
import senntyou.sbs.admin.dao.AdminUserPermissionRelationDao;
import senntyou.sbs.admin.dao.AdminUserRoleRelationDao;
import senntyou.sbs.admin.dto.AdminUserParam;
import senntyou.sbs.admin.dto.UpdateAdminUserPasswordParam;
import senntyou.sbs.admin.security.util.JwtTokenUtil;
import senntyou.sbs.admin.service.AdminUserCacheService;
import senntyou.sbs.admin.service.AdminUserService;
import senntyou.sbs.mbg.mapper.AdminLoginLogMapper;
import senntyou.sbs.mbg.mapper.AdminUserMapper;
import senntyou.sbs.mbg.mapper.AdminUserPermissionRelationMapper;
import senntyou.sbs.mbg.mapper.AdminUserRoleRelationMapper;
import senntyou.sbs.mbg.model.AdminLoginLog;
import senntyou.sbs.mbg.model.AdminPermission;
import senntyou.sbs.mbg.model.AdminResource;
import senntyou.sbs.mbg.model.AdminRole;
import senntyou.sbs.mbg.model.AdminUser;
import senntyou.sbs.mbg.model.AdminUserExample;
import senntyou.sbs.mbg.model.AdminUserPermissionRelation;
import senntyou.sbs.mbg.model.AdminUserPermissionRelationExample;
import senntyou.sbs.mbg.model.AdminUserRoleRelation;
import senntyou.sbs.mbg.model.AdminUserRoleRelationExample;

/** AdminUserService实现类 Created by macro on 2018/4/26. */
@Service
public class AdminUserServiceImpl implements AdminUserService {
  private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserServiceImpl.class);
  @Autowired private JwtTokenUtil jwtTokenUtil;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private AdminUserMapper adminMapper;
  @Autowired private AdminUserRoleRelationMapper adminRoleRelationMapper;
  @Autowired private AdminUserRoleRelationDao adminRoleRelationDao;
  @Autowired private AdminUserPermissionRelationMapper adminPermissionRelationMapper;
  @Autowired private AdminUserPermissionRelationDao adminPermissionRelationDao;
  @Autowired private AdminLoginLogMapper loginLogMapper;
  @Autowired private AdminUserCacheService adminCacheService;

  @Override
  public AdminUser getAdminByUsername(String username) {
    AdminUser admin = adminCacheService.getAdmin(username);
    if (admin != null) return admin;
    AdminUserExample example = new AdminUserExample();
    example.createCriteria().andUsernameEqualTo(username);
    List<AdminUser> adminList = adminMapper.selectByExample(example);
    if (adminList != null && adminList.size() > 0) {
      admin = adminList.get(0);
      adminCacheService.setAdmin(admin);
      return admin;
    }
    return null;
  }

  @Override
  public AdminUser register(AdminUserParam umsAdminParam) {
    AdminUser umsAdmin = new AdminUser();
    BeanUtils.copyProperties(umsAdminParam, umsAdmin);
    umsAdmin.setCreateTime(new Date());
    umsAdmin.setStatus(1);
    // 查询是否有相同用户名的用户
    AdminUserExample example = new AdminUserExample();
    example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
    List<AdminUser> umsAdminList = adminMapper.selectByExample(example);
    if (umsAdminList.size() > 0) {
      return null;
    }
    // 将密码进行加密操作
    String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
    umsAdmin.setPassword(encodePassword);
    adminMapper.insert(umsAdmin);
    return umsAdmin;
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
      //            updateLoginTimeByUsername(username);
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
    AdminUser admin = getAdminByUsername(username);
    if (admin == null) return;
    AdminLoginLog loginLog = new AdminLoginLog();
    loginLog.setUserId(admin.getId());
    loginLog.setCreateTime(new Date());
    ServletRequestAttributes attributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    loginLog.setIp(request.getRemoteAddr());
    loginLogMapper.insert(loginLog);
  }

  /** 根据用户名修改登录时间 */
  private void updateLoginTimeByUsername(String username) {
    AdminUser record = new AdminUser();
    record.setLastLoginTime(new Date());
    AdminUserExample example = new AdminUserExample();
    example.createCriteria().andUsernameEqualTo(username);
    adminMapper.updateByExampleSelective(record, example);
  }

  @Override
  public String refreshToken(String oldToken) {
    return jwtTokenUtil.refreshHeadToken(oldToken);
  }

  @Override
  public AdminUser getItem(Long id) {
    return adminMapper.selectByPrimaryKey(id);
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
    return adminMapper.selectByExample(example);
  }

  @Override
  public int update(Long id, AdminUser admin) {
    admin.setId(id);
    AdminUser rawAdmin = adminMapper.selectByPrimaryKey(id);
    if (rawAdmin.getPassword().equals(admin.getPassword())) {
      // 与原加密密码相同的不需要修改
      admin.setPassword(null);
    } else {
      // 与原加密密码不同的需要加密修改
      if (StrUtil.isEmpty(admin.getPassword())) {
        admin.setPassword(null);
      } else {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
      }
    }
    int count = adminMapper.updateByPrimaryKeySelective(admin);
    adminCacheService.delAdmin(id);
    return count;
  }

  @Override
  public int delete(Long id) {
    adminCacheService.delAdmin(id);
    int count = adminMapper.deleteByPrimaryKey(id);
    adminCacheService.delResourceList(id);
    return count;
  }

  @Override
  public int updateRole(Long userId, List<Long> roleIds) {
    int count = roleIds == null ? 0 : roleIds.size();
    // 先删除原来的关系
    AdminUserRoleRelationExample adminRoleRelationExample = new AdminUserRoleRelationExample();
    adminRoleRelationExample.createCriteria().andUserIdEqualTo(userId);
    adminRoleRelationMapper.deleteByExample(adminRoleRelationExample);
    // 建立新关系
    if (!CollectionUtils.isEmpty(roleIds)) {
      List<AdminUserRoleRelation> list = new ArrayList<>();
      for (Long roleId : roleIds) {
        AdminUserRoleRelation roleRelation = new AdminUserRoleRelation();
        roleRelation.setUserId(userId);
        roleRelation.setRoleId(roleId);
        list.add(roleRelation);
      }
      adminRoleRelationDao.insertList(list);
    }
    adminCacheService.delResourceList(userId);
    return count;
  }

  @Override
  public List<AdminRole> getRoleList(Long userId) {
    return adminRoleRelationDao.getRoleList(userId);
  }

  @Override
  public List<AdminResource> getResourceList(Long userId) {
    List<AdminResource> resourceList = adminCacheService.getResourceList(userId);
    if (CollUtil.isNotEmpty(resourceList)) {
      return resourceList;
    }
    resourceList = adminRoleRelationDao.getResourceList(userId);
    if (CollUtil.isNotEmpty(resourceList)) {
      adminCacheService.setResourceList(userId, resourceList);
    }
    return resourceList;
  }

  @Override
  public int updatePermission(Long userId, List<Long> permissionIds) {
    // 删除原所有权限关系
    AdminUserPermissionRelationExample relationExample = new AdminUserPermissionRelationExample();
    relationExample.createCriteria().andUserIdEqualTo(userId);
    adminPermissionRelationMapper.deleteByExample(relationExample);
    // 获取用户所有角色权限
    List<AdminPermission> permissionList = adminRoleRelationDao.getRolePermissionList(userId);
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
      relationList.addAll(convert(userId, 1, addPermissionIdList));
      relationList.addAll(convert(userId, -1, subPermissionIdList));
      return adminPermissionRelationDao.insertList(relationList);
    }
    return 0;
  }

  /** 将+-权限关系转化为对象 */
  private List<AdminUserPermissionRelation> convert(
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
    return adminRoleRelationDao.getPermissionList(userId);
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
    List<AdminUser> adminList = adminMapper.selectByExample(example);
    if (CollUtil.isEmpty(adminList)) {
      return -2;
    }
    AdminUser umsAdmin = adminList.get(0);
    if (!passwordEncoder.matches(param.getOldPassword(), umsAdmin.getPassword())) {
      return -3;
    }
    umsAdmin.setPassword(passwordEncoder.encode(param.getNewPassword()));
    adminMapper.updateByPrimaryKey(umsAdmin);
    adminCacheService.delAdmin(umsAdmin.getId());
    return 1;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    // 获取用户信息
    AdminUser admin = getAdminByUsername(username);
    if (admin != null) {
      List<AdminResource> resourceList = getResourceList(admin.getId());
      return new AdminUserDetails(admin, resourceList);
    }
    throw new UsernameNotFoundException("用户名或密码错误");
  }
}
