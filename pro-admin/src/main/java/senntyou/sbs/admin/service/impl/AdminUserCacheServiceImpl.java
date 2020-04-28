package senntyou.sbs.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import senntyou.sbs.admin.dao.AdminUserRoleRelationDao;
import senntyou.sbs.admin.security.service.RedisService;
import senntyou.sbs.admin.service.AdminUserCacheService;
import senntyou.sbs.admin.service.AdminUserService;
import senntyou.sbs.mbg.mapper.AdminUserRoleRelationMapper;
import senntyou.sbs.mbg.model.AdminResource;
import senntyou.sbs.mbg.model.AdminUser;
import senntyou.sbs.mbg.model.AdminUserRoleRelation;
import senntyou.sbs.mbg.model.AdminUserRoleRelationExample;

/** AdminUserCacheService实现类 */
@Service
public class AdminUserCacheServiceImpl implements AdminUserCacheService {
  @Autowired private AdminUserService adminService;
  @Autowired private RedisService redisService;
  @Autowired private AdminUserRoleRelationMapper adminRoleRelationMapper;
  @Autowired private AdminUserRoleRelationDao adminRoleRelationDao;

  @Value("${redis.database}")
  private String REDIS_DATABASE;

  @Value("${redis.expire.common}")
  private Long REDIS_EXPIRE;

  @Value("${redis.key.admin}")
  private String REDIS_KEY_ADMIN;

  @Value("${redis.key.resourceList}")
  private String REDIS_KEY_RESOURCE_LIST;

  @Override
  public void delUser(Long userId) {
    AdminUser user = adminService.getItem(userId);
    if (user != null) {
      String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + user.getUsername();
      redisService.del(key);
    }
  }

  @Override
  public void delResourceList(Long userId) {
    String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + userId;
    redisService.del(key);
  }

  @Override
  public void delResourceListByRole(Long roleId) {
    AdminUserRoleRelationExample example = new AdminUserRoleRelationExample();
    example.createCriteria().andRoleIdEqualTo(roleId);
    List<AdminUserRoleRelation> relationList = adminRoleRelationMapper.selectByExample(example);
    if (CollUtil.isNotEmpty(relationList)) {
      String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
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
    example.createCriteria().andRoleIdIn(roleIds);
    List<AdminUserRoleRelation> relationList = adminRoleRelationMapper.selectByExample(example);
    if (CollUtil.isNotEmpty(relationList)) {
      String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
      List<String> keys =
          relationList.stream()
              .map(relation -> keyPrefix + relation.getUserId())
              .collect(Collectors.toList());
      redisService.del(keys);
    }
  }

  @Override
  public void delResourceListByResource(Long resourceId) {
    List<Long> userIdList = adminRoleRelationDao.getAdminIdList(resourceId);
    if (CollUtil.isNotEmpty(userIdList)) {
      String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
      List<String> keys =
          userIdList.stream().map(userId -> keyPrefix + userId).collect(Collectors.toList());
      redisService.del(keys);
    }
  }

  @Override
  public AdminUser getUser(String username) {
    String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + username;
    return (AdminUser) redisService.get(key);
  }

  @Override
  public void setUser(AdminUser adminUser) {
    String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + adminUser.getUsername();
    redisService.set(key, adminUser, REDIS_EXPIRE);
  }

  @Override
  public List<AdminResource> getResourceList(Long userId) {
    String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + userId;
    return (List<AdminResource>) redisService.get(key);
  }

  @Override
  public void setResourceList(Long userId, List<AdminResource> resourceList) {
    String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + userId;
    redisService.set(key, resourceList, REDIS_EXPIRE);
  }
}
