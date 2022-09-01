package dr.sbs.mbg.mapper;

import dr.sbs.mbg.model.AdminRolePermissionRelation;
import dr.sbs.mbg.model.AdminRolePermissionRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminRolePermissionRelationMapper {
    long countByExample(AdminRolePermissionRelationExample example);

    int deleteByExample(AdminRolePermissionRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AdminRolePermissionRelation record);

    int insertSelective(AdminRolePermissionRelation record);

    List<AdminRolePermissionRelation> selectByExample(AdminRolePermissionRelationExample example);

    AdminRolePermissionRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AdminRolePermissionRelation record, @Param("example") AdminRolePermissionRelationExample example);

    int updateByExample(@Param("record") AdminRolePermissionRelation record, @Param("example") AdminRolePermissionRelationExample example);

    int updateByPrimaryKeySelective(AdminRolePermissionRelation record);

    int updateByPrimaryKey(AdminRolePermissionRelation record);
}