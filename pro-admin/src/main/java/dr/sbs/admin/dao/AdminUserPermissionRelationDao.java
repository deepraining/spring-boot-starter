package dr.sbs.admin.dao;

import dr.sbs.mbg.model.AdminUserPermissionRelation;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/** 用户权限自定义Dao */
public interface AdminUserPermissionRelationDao {
  int insertList(@Param("list") List<AdminUserPermissionRelation> list);
}
