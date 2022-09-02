package dr.sbs.admin.dao;

import dr.sbs.mbg.model.AdminMenu;
import dr.sbs.mbg.model.AdminResource;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/** 后台用户角色自定义Dao */
public interface AdminRoleDao {
  List<AdminMenu> getMenuList(@Param("roleId") Long roleId);

  List<AdminResource> getResourceList(@Param("roleId") Long roleId);
}
