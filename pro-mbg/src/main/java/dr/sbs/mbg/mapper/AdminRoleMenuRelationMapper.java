package dr.sbs.mbg.mapper;

import dr.sbs.mbg.model.AdminRoleMenuRelation;
import dr.sbs.mbg.model.AdminRoleMenuRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminRoleMenuRelationMapper {
    long countByExample(AdminRoleMenuRelationExample example);

    int deleteByExample(AdminRoleMenuRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AdminRoleMenuRelation record);

    int insertSelective(AdminRoleMenuRelation record);

    List<AdminRoleMenuRelation> selectByExample(AdminRoleMenuRelationExample example);

    AdminRoleMenuRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AdminRoleMenuRelation record, @Param("example") AdminRoleMenuRelationExample example);

    int updateByExample(@Param("record") AdminRoleMenuRelation record, @Param("example") AdminRoleMenuRelationExample example);

    int updateByPrimaryKeySelective(AdminRoleMenuRelation record);

    int updateByPrimaryKey(AdminRoleMenuRelation record);
}