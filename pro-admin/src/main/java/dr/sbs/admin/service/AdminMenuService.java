package dr.sbs.admin.service;

import dr.sbs.admin.dto.AdminMenuNode;
import dr.sbs.mbg.model.AdminMenu;
import java.util.List;

/** 后台菜单管理Service */
public interface AdminMenuService {
  /** 创建后台菜单 */
  int create(AdminMenu adminMenu);

  /** 修改后台菜单 */
  int update(Long id, AdminMenu adminMenu);

  /** 根据ID获取菜单详情 */
  AdminMenu getItem(Long id);

  /** 根据ID删除菜单 */
  int delete(Long id);

  /** 分页查询后台菜单 */
  List<AdminMenu> list(Long parentId, Integer pageSize, Integer pageNum);

  /** 树形结构返回所有菜单列表 */
  List<AdminMenuNode> treeList();

  /** 修改菜单显示状态 */
  int updateHidden(Long id, Integer hidden);
}
