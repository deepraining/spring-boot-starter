package senntyou.sbs.admin.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import senntyou.sbs.admin.dto.AdminPermissionNode;
import senntyou.sbs.admin.service.AdminPermissionService;
import senntyou.sbs.mbg.mapper.AdminPermissionMapper;
import senntyou.sbs.mbg.model.AdminPermission;
import senntyou.sbs.mbg.model.AdminPermissionExample;

/** 后台用户权限管理Service实现类 */
@Service
public class AdminPermissionServiceImpl implements AdminPermissionService {
  @Autowired private AdminPermissionMapper permissionMapper;

  @Override
  public int create(AdminPermission permission) {
    permission.setStatus(1);
    permission.setSort(0);
    return permissionMapper.insertSelective(permission);
  }

  @Override
  public int update(Long id, AdminPermission permission) {
    permission.setId(id);
    return permissionMapper.updateByPrimaryKeySelective(permission);
  }

  @Override
  public int delete(List<Long> ids) {
    AdminPermissionExample example = new AdminPermissionExample();
    example.createCriteria().andIdIn(ids);
    return permissionMapper.deleteByExample(example);
  }

  @Override
  public List<AdminPermissionNode> treeList() {
    List<AdminPermission> permissionList =
        permissionMapper.selectByExample(new AdminPermissionExample());
    List<AdminPermissionNode> result =
        permissionList.stream()
            .filter(permission -> permission.getParentId().equals(0L))
            .map(permission -> convertPermissionNode(permission, permissionList))
            .collect(Collectors.toList());
    return result;
  }

  @Override
  public List<AdminPermission> list() {
    return permissionMapper.selectByExample(new AdminPermissionExample());
  }

  /** 将权限转换为带有子级的权限对象 当找不到子级权限的时候map操作不会再递归调用convertPermissionNode */
  private AdminPermissionNode convertPermissionNode(
      AdminPermission permission, List<AdminPermission> permissionList) {
    AdminPermissionNode node = new AdminPermissionNode();
    BeanUtils.copyProperties(permission, node);
    List<AdminPermissionNode> children =
        permissionList.stream()
            .filter(subPermission -> subPermission.getParentId().equals(permission.getId()))
            .map(subPermission -> convertPermissionNode(subPermission, permissionList))
            .collect(Collectors.toList());
    node.setChildren(children);
    return node;
  }
}
