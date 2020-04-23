package senntyou.sbs.mbg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import senntyou.sbs.mbg.model.AdminRoleResourceRelation;
import senntyou.sbs.mbg.model.AdminRoleResourceRelationExample;

public interface AdminRoleResourceRelationMapper {
    long countByExample(AdminRoleResourceRelationExample example);

    int deleteByExample(AdminRoleResourceRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AdminRoleResourceRelation record);

    int insertSelective(AdminRoleResourceRelation record);

    List<AdminRoleResourceRelation> selectByExample(AdminRoleResourceRelationExample example);

    AdminRoleResourceRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AdminRoleResourceRelation record, @Param("example") AdminRoleResourceRelationExample example);

    int updateByExample(@Param("record") AdminRoleResourceRelation record, @Param("example") AdminRoleResourceRelationExample example);

    int updateByPrimaryKeySelective(AdminRoleResourceRelation record);

    int updateByPrimaryKey(AdminRoleResourceRelation record);
}