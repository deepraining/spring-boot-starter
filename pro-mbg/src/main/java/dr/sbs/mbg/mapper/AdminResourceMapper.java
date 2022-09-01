package dr.sbs.mbg.mapper;

import dr.sbs.mbg.model.AdminResource;
import dr.sbs.mbg.model.AdminResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminResourceMapper {
    long countByExample(AdminResourceExample example);

    int deleteByExample(AdminResourceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AdminResource record);

    int insertSelective(AdminResource record);

    List<AdminResource> selectByExample(AdminResourceExample example);

    AdminResource selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AdminResource record, @Param("example") AdminResourceExample example);

    int updateByExample(@Param("record") AdminResource record, @Param("example") AdminResourceExample example);

    int updateByPrimaryKeySelective(AdminResource record);

    int updateByPrimaryKey(AdminResource record);
}