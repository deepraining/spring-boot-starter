package dr.sbs.admin.service;

import dr.sbs.admin.dto.AdminPermissionNode;
import dr.sbs.mbg.model.AdminPermission;
import java.util.List;

/** 后台用户权限管理Service */
public interface AdminPermissionService {
  /** 添加权限 */
  int create(AdminPermission permission);

  /** 修改权限 */
  int update(Long id, AdminPermission permission);

  /** 批量删除权限 */
  int delete(List<Long> ids);

  /** 以层级结构返回所有权限 */
  List<AdminPermissionNode> treeList();

  /** 获取所有权限 */
  List<AdminPermission> list();
}
