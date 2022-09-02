package dr.sbs.admin.service;

import dr.sbs.mbg.model.AdminMenu;
import dr.sbs.mbg.model.AdminResource;
import dr.sbs.mbg.model.AdminRole;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/** 后台角色管理Service */
public interface AdminRoleService {
  /** 添加角色 */
  int create(AdminRole role);

  /** 修改角色信息 */
  int update(Long id, AdminRole role);

  /** 批量删除角色 */
  int delete(List<Long> ids);

  /** 获取所有角色列表 */
  List<AdminRole> list();

  /** 分页获取角色列表 */
  List<AdminRole> list(String keyword, Integer pageSize, Integer pageNum);

  /** 获取角色相关菜单 */
  List<AdminMenu> listMenu(Long roleId);

  /** 获取角色相关资源 */
  List<AdminResource> listResource(Long roleId);

  /** 给角色分配菜单 */
  @Transactional
  int allocMenu(Long roleId, List<Long> menuIds);

  /** 给角色分配资源 */
  @Transactional
  int allocResource(Long roleId, List<Long> resourceIds);
}
