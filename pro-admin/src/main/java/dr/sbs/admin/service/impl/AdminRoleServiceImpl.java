package dr.sbs.admin.service.impl;

import com.github.pagehelper.PageHelper;
import dr.sbs.admin.dao.AdminRoleDao;
import dr.sbs.admin.service.AdminRoleService;
import dr.sbs.admin.service.AdminUserCacheService;
import dr.sbs.mbg.mapper.AdminRoleMapper;
import dr.sbs.mbg.mapper.AdminRoleMenuRelationMapper;
import dr.sbs.mbg.mapper.AdminRoleResourceRelationMapper;
import dr.sbs.mbg.model.AdminMenu;
import dr.sbs.mbg.model.AdminResource;
import dr.sbs.mbg.model.AdminRole;
import dr.sbs.mbg.model.AdminRoleExample;
import dr.sbs.mbg.model.AdminRoleMenuRelation;
import dr.sbs.mbg.model.AdminRoleMenuRelationExample;
import dr.sbs.mbg.model.AdminRoleResourceRelation;
import dr.sbs.mbg.model.AdminRoleResourceRelationExample;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/** 后台角色管理Service实现类 */
@Service
public class AdminRoleServiceImpl implements AdminRoleService {
  @Autowired private AdminRoleMapper roleMapper;
  @Autowired private AdminRoleMenuRelationMapper roleMenuRelationMapper;
  @Autowired private AdminRoleResourceRelationMapper roleResourceRelationMapper;
  @Autowired private AdminRoleDao roleDao;
  @Autowired private AdminUserCacheService userCacheService;

  @Override
  public int create(AdminRole role) {
    role.setSort(0);
    return roleMapper.insertSelective(role);
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
    userCacheService.delResourceListByRoleIds(ids);
    return count;
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
  public List<AdminMenu> listMenu(Long roleId) {
    return roleDao.getMenuList(roleId);
  }

  @Override
  public List<AdminResource> listResource(Long roleId) {
    return roleDao.getResourceList(roleId);
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
      roleMenuRelationMapper.insertSelective(relation);
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
      roleResourceRelationMapper.insertSelective(relation);
    }
    userCacheService.delResourceListByRole(roleId);
    return resourceIds.size();
  }
}
