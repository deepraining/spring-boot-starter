package senntyou.sbs.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import senntyou.sbs.admin.dao.AdminUserRoleRelationDao;
import senntyou.sbs.admin.service.AdminUserCacheService;
import senntyou.sbs.admin.service.AdminUserService;
import senntyou.sbs.admin.service.RedisService;
import senntyou.sbs.mbg.mapper.AdminUserRoleRelationMapper;
import senntyou.sbs.mbg.model.AdminResource;
import senntyou.sbs.mbg.model.AdminUser;
import senntyou.sbs.mbg.model.AdminUserRoleRelation;
import senntyou.sbs.mbg.model.AdminUserRoleRelationExample;

/** AdminUserCacheService实现类 */
@Service
public class AdminUserCacheServiceImpl implements AdminUserCacheService {
  @Autowired private AdminUserService userService;
  @Autowired private RedisService redisService;
  @Autowired private AdminUserRoleRelationMapper userRoleRelationMapper;
  @Autowired private AdminUserRoleRelationDao userRoleRelationDao;

  @Value("${redis.database}")
  private String redisDatabase;

  @Value("${redis.expire.common}")
  private Long redisExpire;

  @Value("${redis.key.admin}")
  private String redisKeyAdmin;

  @Value("${redis.key.resourceList}")
  private String redisKeyResourceList;

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
    example.createCriteria().andRoleIdEqualTo(roleId);
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
    example.createCriteria().andRoleIdIn(roleIds);
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
