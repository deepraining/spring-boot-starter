package dr.sbs.mbg.mapper;

import dr.sbs.mbg.model.AdminMenu;
import dr.sbs.mbg.model.AdminMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminMenuMapper {
    long countByExample(AdminMenuExample example);

    int deleteByExample(AdminMenuExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AdminMenu record);

    int insertSelective(AdminMenu record);

    List<AdminMenu> selectByExample(AdminMenuExample example);

    AdminMenu selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AdminMenu record, @Param("example") AdminMenuExample example);

    int updateByExample(@Param("record") AdminMenu record, @Param("example") AdminMenuExample example);

    int updateByPrimaryKeySelective(AdminMenu record);

    int updateByPrimaryKey(AdminMenu record);
}