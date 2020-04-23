package senntyou.sbs.admin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import senntyou.sbs.mbg.model.AdminPermission;
import senntyou.sbs.mbg.model.AdminRolePermissionRelation;

/** 后台用户角色管理自定义Dao */
public interface AdminRolePermissionRelationDao {
  /** 批量插入角色和权限关系 */
  int insertList(@Param("list") List<AdminRolePermissionRelation> list);

  /** 根据角色获取权限 */
  List<AdminPermission> getPermissionList(@Param("roleId") Long roleId);
}
