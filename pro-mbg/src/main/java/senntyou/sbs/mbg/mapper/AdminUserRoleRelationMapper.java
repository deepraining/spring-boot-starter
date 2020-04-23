package senntyou.sbs.mbg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import senntyou.sbs.mbg.model.AdminUserRoleRelation;
import senntyou.sbs.mbg.model.AdminUserRoleRelationExample;

public interface AdminUserRoleRelationMapper {
    long countByExample(AdminUserRoleRelationExample example);

    int deleteByExample(AdminUserRoleRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AdminUserRoleRelation record);

    int insertSelective(AdminUserRoleRelation record);

    List<AdminUserRoleRelation> selectByExample(AdminUserRoleRelationExample example);

    AdminUserRoleRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AdminUserRoleRelation record, @Param("example") AdminUserRoleRelationExample example);

    int updateByExample(@Param("record") AdminUserRoleRelation record, @Param("example") AdminUserRoleRelationExample example);

    int updateByPrimaryKeySelective(AdminUserRoleRelation record);

    int updateByPrimaryKey(AdminUserRoleRelation record);
}