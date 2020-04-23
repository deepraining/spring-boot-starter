package senntyou.sbs.mbg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import senntyou.sbs.mbg.model.AdminUserPermissionRelation;
import senntyou.sbs.mbg.model.AdminUserPermissionRelationExample;

public interface AdminUserPermissionRelationMapper {
    long countByExample(AdminUserPermissionRelationExample example);

    int deleteByExample(AdminUserPermissionRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AdminUserPermissionRelation record);

    int insertSelective(AdminUserPermissionRelation record);

    List<AdminUserPermissionRelation> selectByExample(AdminUserPermissionRelationExample example);

    AdminUserPermissionRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AdminUserPermissionRelation record, @Param("example") AdminUserPermissionRelationExample example);

    int updateByExample(@Param("record") AdminUserPermissionRelation record, @Param("example") AdminUserPermissionRelationExample example);

    int updateByPrimaryKeySelective(AdminUserPermissionRelation record);

    int updateByPrimaryKey(AdminUserPermissionRelation record);
}