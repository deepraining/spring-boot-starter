package dr.sbs.admin.dao;

import dr.sbs.mbg.model.AdminMenu;
import dr.sbs.mbg.model.AdminResource;
import dr.sbs.mbg.model.AdminRole;
import dr.sbs.mbg.model.AdminUserRoleRelation;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/** 后台用户与角色管理自定义Dao */
public interface AdminUserRoleRelationDao {
  /** 批量插入用户角色关系 */
  int insertList(@Param("list") List<AdminUserRoleRelation> adminRoleRelationList);

  /** 获取用于所有角色 */
  List<AdminRole> getRoleList(@Param("userId") Long userId);

  /** 获取用户所有可访问菜单 */
  List<AdminMenu> getMenuList(@Param("userId") Long userId);

  /** 获取用户所有可访问资源 */
  List<AdminResource> getResourceList(@Param("userId") Long userId);

  /** 获取资源相关用户ID列表 */
  List<Long> getAdminIdList(@Param("resourceId") Long resourceId);
}
