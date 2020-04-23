package senntyou.sbs.admin.service.impl;

import com.github.pagehelper.PageHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import senntyou.sbs.admin.dao.AdminRoleDao;
import senntyou.sbs.admin.dao.AdminRolePermissionRelationDao;
import senntyou.sbs.admin.service.AdminRoleService;
import senntyou.sbs.admin.service.AdminUserCacheService;
import senntyou.sbs.mbg.mapper.AdminRoleMapper;
import senntyou.sbs.mbg.mapper.AdminRoleMenuRelationMapper;
import senntyou.sbs.mbg.mapper.AdminRolePermissionRelationMapper;
import senntyou.sbs.mbg.mapper.AdminRoleResourceRelationMapper;
import senntyou.sbs.mbg.model.AdminMenu;
import senntyou.sbs.mbg.model.AdminPermission;
import senntyou.sbs.mbg.model.AdminResource;
import senntyou.sbs.mbg.model.AdminRole;
import senntyou.sbs.mbg.model.AdminRoleExample;
import senntyou.sbs.mbg.model.AdminRoleMenuRelation;
import senntyou.sbs.mbg.model.AdminRoleMenuRelationExample;
import senntyou.sbs.mbg.model.AdminRolePermissionRelation;
import senntyou.sbs.mbg.model.AdminRolePermissionRelationExample;
import senntyou.sbs.mbg.model.AdminRoleResourceRelation;
import senntyou.sbs.mbg.model.AdminRoleResourceRelationExample;

/** 后台角色管理Service实现类 Created by macro on 2018/9/30. */
@Service
public class AdminRoleServiceImpl implements AdminRoleService {
  @Autowired private AdminRoleMapper roleMapper;
  @Autowired private AdminRolePermissionRelationMapper rolePermissionRelationMapper;
  @Autowired private AdminRoleMenuRelationMapper roleMenuRelationMapper;
  @Autowired private AdminRoleResourceRelationMapper roleResourceRelationMapper;
  @Autowired private AdminRolePermissionRelationDao rolePermissionRelationDao;
  @Autowired private AdminRoleDao roleDao;
  @Autowired private AdminUserCacheService adminCacheService;

  @Override
  public int create(AdminRole role) {
    role.setCreateTime(new Date());
    role.setUserCount(0);
    role.setSort(0);
    return roleMapper.insert(role);
  }

  @Override
  public int update(Long id, AdminRole role) {
    role.setId(id);
    return roleMapper.updateByPrimaryKeySelective(role);
  }

  @Override
  public int delete(List<Long> ids) {
    AdminRoleExample example = new AdminRoleExample();
    example.createCriteria().andIdIn(ids);
    int count = roleMapper.deleteByExample(example);
    adminCacheService.delResourceListByRoleIds(ids);
    return count;
  }

  @Override
  public List<AdminPermission> getPermissionList(Long roleId) {
    return rolePermissionRelationDao.getPermissionList(roleId);
  }

  @Override
  public int updatePermission(Long roleId, List<Long> permissionIds) {
    // 先删除原有关系
    AdminRolePermissionRelationExample example = new AdminRolePermissionRelationExample();
    example.createCriteria().andRoleIdEqualTo(roleId);
    rolePermissionRelationMapper.deleteByExample(example);
    // 批量插入新关系
    List<AdminRolePermissionRelation> relationList = new ArrayList<>();
    for (Long permissionId : permissionIds) {
      AdminRolePermissionRelation relation = new AdminRolePermissionRelation();
      relation.setRoleId(roleId);
      relation.setPermissionId(permissionId);
      relationList.add(relation);
    }
    return rolePermissionRelationDao.insertList(relationList);
  }

  @Override
  public List<AdminRole> list() {
    return roleMapper.selectByExample(new AdminRoleExample());
  }

  @Override
  public List<AdminRole> list(String keyword, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    AdminRoleExample example = new AdminRoleExample();
    if (!StringUtils.isEmpty(keyword)) {
      example.createCriteria().andNameLike("%" + keyword + "%");
    }
    return roleMapper.selectByExample(example);
  }

  @Override
  public List<AdminMenu> getMenuList(Long userId) {
    return roleDao.getMenuList(userId);
  }

  @Override
  public List<AdminMenu> listMenu(Long roleId) {
    return roleDao.getMenuListByRoleId(roleId);
  }

  @Override
  public List<AdminResource> listResource(Long roleId) {
    return roleDao.getResourceListByRoleId(roleId);
  }

  @Override
  public int allocMenu(Long roleId, List<Long> menuIds) {
    // 先删除原有关系
    AdminRoleMenuRelationExample example = new AdminRoleMenuRelationExample();
    example.createCriteria().andRoleIdEqualTo(roleId);
    roleMenuRelationMapper.deleteByExample(example);
    // 批量插入新关系
    for (Long menuId : menuIds) {
      AdminRoleMenuRelation relation = new AdminRoleMenuRelation();
      relation.setRoleId(roleId);
      relation.setMenuId(menuId);
      roleMenuRelationMapper.insert(relation);
    }
    return menuIds.size();
  }

  @Override
  public int allocResource(Long roleId, List<Long> resourceIds) {
    // 先删除原有关系
    AdminRoleResourceRelationExample example = new AdminRoleResourceRelationExample();
    example.createCriteria().andRoleIdEqualTo(roleId);
    roleResourceRelationMapper.deleteByExample(example);
    // 批量插入新关系
    for (Long resourceId : resourceIds) {
      AdminRoleResourceRelation relation = new AdminRoleResourceRelation();
      relation.setRoleId(roleId);
      relation.setResourceId(resourceId);
      roleResourceRelationMapper.insert(relation);
    }
    adminCacheService.delResourceListByRole(roleId);
    return resourceIds.size();
  }
}
