package senntyou.sbs.admin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import senntyou.sbs.mbg.model.AdminPermission;
import senntyou.sbs.mbg.model.AdminResource;
import senntyou.sbs.mbg.model.AdminRole;
import senntyou.sbs.mbg.model.AdminUserRoleRelation;

/** 后台用户与角色管理自定义Dao Created by macro on 2018/10/8. */
public interface AdminUserRoleRelationDao {
  /** 批量插入用户角色关系 */
  int insertList(@Param("list") List<AdminUserRoleRelation> adminRoleRelationList);

  /** 获取用于所有角色 */
  List<AdminRole> getRoleList(@Param("userId") Long userId);

  /** 获取用户所有角色权限 */
  List<AdminPermission> getRolePermissionList(@Param("userId") Long userId);

  /** 获取用户所有权限(包括+-权限) */
  List<AdminPermission> getPermissionList(@Param("userId") Long userId);

  /** 获取用户所有可访问资源 */
  List<AdminResource> getResourceList(@Param("userId") Long userId);

  /** 获取资源相关用户ID列表 */
  List<Long> getAdminIdList(@Param("resourceId") Long resourceId);
}
