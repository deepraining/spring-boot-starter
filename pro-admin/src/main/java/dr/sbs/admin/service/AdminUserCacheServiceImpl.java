package dr.sbs.admin.service;

import cn.hutool.core.collection.CollUtil;
import dr.sbs.admin.dao.AdminUserRoleRelationDao;
import dr.sbs.mbg.mapper.AdminUserRoleRelationMapper;
import dr.sbs.mbg.model.AdminResource;
import dr.sbs.mbg.model.AdminUser;
import dr.sbs.mbg.model.AdminUserRoleRelation;
import dr.sbs.mbg.model.AdminUserRoleRelationExample;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** AdminUserCacheService实现类 */
@Service
public class AdminUserCacheServiceImpl implements AdminUserCacheService {
  @Autowired private AdminUserService userService;
  @Autowired private RedisService redisService;
  @Autowired private AdminUserRoleRelationMapper userRoleRelationMapper;
  @Autowired private AdminUserRoleRelationDao userRoleRelationDao;

  private String redisDatabase = "sbsAdmin";
  // 24 hours
  private Long redisExpire = 86400L;
  private String redisKeyAdmin = "admin";
  private String redisKeyResourceList = "resourceList";

  @Override
  public void delUser(Long userId) {
    AdminUser user = userService.getItem(userId);
    if (user != null) {
      String key = redisDatabase + ":" + redisKeyAdmin + ":" + user.getUsername();
      redisService.del(key);
    }
  }

  @Override
  public void delResourceList(Long userId) {
    String key = redisDatabase + ":" + redisKeyResourceList + ":" + userId;
    redisService.del(key);
  }

  @Override
  public void delResourceListByRole(Long roleId) {
    AdminUserRoleRelationExample example = new AdminUserRoleRelationExample();
    example.createCriteria().andRoleIdEqualTo(roleId).andStatusEqualTo(1);
    List<AdminUserRoleRelation> relationList = userRoleRelationMapper.selectByExample(example);
    if (CollUtil.isNotEmpty(relationList)) {
      String keyPrefix = redisDatabase + ":" + redisKeyResourceList + ":";
      List<String> keys =
          relationList.stream()
              .map(relation -> keyPrefix + relation.getUserId())
              .collect(Collectors.toList());
      redisService.del(keys);
    }
  }

  @Override
  public void delResourceListByRoleIds(List<Long> roleIds) {
    AdminUserRoleRelationExample example = new AdminUserRoleRelationExample();
    example.createCriteria().andRoleIdIn(roleIds).andStatusEqualTo(1);
    List<AdminUserRoleRelation> relationList = userRoleRelationMapper.selectByExample(example);
    if (CollUtil.isNotEmpty(relationList)) {
      String keyPrefix = redisDatabase + ":" + redisKeyResourceList + ":";
      List<String> keys =
          relationList.stream()
              .map(relation -> keyPrefix + relation.getUserId())
              .collect(Collectors.toList());
      redisService.del(keys);
    }
  }

  @Override
  public void delResourceListByResource(Long resourceId) {
    List<Long> userIdList = userRoleRelationDao.getAdminIdList(resourceId);
    if (CollUtil.isNotEmpty(userIdList)) {
      String keyPrefix = redisDatabase + ":" + redisKeyResourceList + ":";
      List<String> keys =
          userIdList.stream().map(userId -> keyPrefix + userId).collect(Collectors.toList());
      redisService.del(keys);
    }
  }

  @Override
  public AdminUser getUser(String username) {
    String key = redisDatabase + ":" + redisKeyAdmin + ":" + username;
    return (AdminUser) redisService.get(key);
  }

  @Override
  public void setUser(AdminUser adminUser) {
    String key = redisDatabase + ":" + redisKeyAdmin + ":" + adminUser.getUsername();
    redisService.set(key, adminUser, redisExpire);
  }

  @Override
  public List<AdminResource> getResourceList(Long userId) {
    String key = redisDatabase + ":" + redisKeyResourceList + ":" + userId;
    return (List<AdminResource>) redisService.get(key);
  }

  @Override
  public void setResourceList(Long userId, List<AdminResource> resourceList) {
    String key = redisDatabase + ":" + redisKeyResourceList + ":" + userId;
    redisService.set(key, resourceList, redisExpire);
  }
}
