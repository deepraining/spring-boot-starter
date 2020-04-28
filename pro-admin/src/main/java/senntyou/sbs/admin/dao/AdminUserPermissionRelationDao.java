package senntyou.sbs.admin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import senntyou.sbs.mbg.model.AdminUserPermissionRelation;

/** 用户权限自定义Dao */
public interface AdminUserPermissionRelationDao {
  int insertList(@Param("list") List<AdminUserPermissionRelation> list);
}
