package dr.sbs.admin.dao;

import dr.sbs.mbg.model.AdminPermission;
import dr.sbs.mbg.model.AdminRolePermissionRelation;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/** 后台用户角色管理自定义Dao */
public interface AdminRolePermissionRelationDao {
  /** 批量插入角色和权限关系 */
  int insertList(@Param("list") List<AdminRolePermissionRelation> list);

  /** 根据角色获取权限 */
  List<AdminPermission> getPermissionList(@Param("roleId") Long roleId);
}
