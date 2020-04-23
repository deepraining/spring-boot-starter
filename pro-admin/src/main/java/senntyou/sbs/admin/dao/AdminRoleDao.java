package senntyou.sbs.admin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import senntyou.sbs.mbg.model.AdminMenu;
import senntyou.sbs.mbg.model.AdminResource;

/** 后台用户角色自定义Dao */
public interface AdminRoleDao {
  List<AdminMenu> getMenuList(@Param("userId") Long userId);

  List<AdminMenu> getMenuListByRoleId(@Param("roleId") Long roleId);

  List<AdminResource> getResourceListByRoleId(@Param("roleId") Long roleId);
}
