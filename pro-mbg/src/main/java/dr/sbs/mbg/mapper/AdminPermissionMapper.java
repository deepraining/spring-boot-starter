package dr.sbs.mbg.mapper;

import dr.sbs.mbg.model.AdminPermission;
import dr.sbs.mbg.model.AdminPermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminPermissionMapper {
    long countByExample(AdminPermissionExample example);

    int deleteByExample(AdminPermissionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AdminPermission record);

    int insertSelective(AdminPermission record);

    List<AdminPermission> selectByExample(AdminPermissionExample example);

    AdminPermission selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AdminPermission record, @Param("example") AdminPermissionExample example);

    int updateByExample(@Param("record") AdminPermission record, @Param("example") AdminPermissionExample example);

    int updateByPrimaryKeySelective(AdminPermission record);

    int updateByPrimaryKey(AdminPermission record);
}