package dr.sbs.admin.service;

import com.github.pagehelper.PageHelper;
import dr.sbs.admin.dao.AdminRoleDao;
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
    AdminRole adminRole = new AdminRole();
    adminRole.setStatus(-1);
    int count = roleMapper.updateByExampleSelective(adminRole, example);
    userCacheService.delResourceListByRoleIds(ids);
    return count;
  }

  @Override
  public List<AdminRole> list() {
    AdminRoleExample example = new AdminRoleExample();
    example.createCriteria().andStatusNotEqualTo(-1);
    return roleMapper.selectByExample(example);
  }

  @Override
  public List<AdminRole> list(String keyword, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    AdminRoleExample example = new AdminRoleExample();
    AdminRoleExample.Criteria criteria = example.createCriteria();
    criteria.andStatusNotEqualTo(-1);
    if (!StringUtils.isEmpty(keyword)) {
      criteria.andNameLike("%" + keyword + "%");
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
    AdminRoleMenuRelation adminRoleMenuRelation = new AdminRoleMenuRelation();
    adminRoleMenuRelation.setStatus(-1);
    roleMenuRelationMapper.updateByExampleSelective(adminRoleMenuRelation, example);
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
    AdminRoleResourceRelation adminRoleResourceRelation = new AdminRoleResourceRelation();
    adminRoleResourceRelation.setStatus(-1);
    roleResourceRelationMapper.updateByExampleSelective(adminRoleResourceRelation, example);
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
