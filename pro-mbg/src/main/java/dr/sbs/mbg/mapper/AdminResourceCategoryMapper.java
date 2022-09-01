package dr.sbs.mbg.mapper;

import dr.sbs.mbg.model.AdminResourceCategory;
import dr.sbs.mbg.model.AdminResourceCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminResourceCategoryMapper {
    long countByExample(AdminResourceCategoryExample example);

    int deleteByExample(AdminResourceCategoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AdminResourceCategory record);

    int insertSelective(AdminResourceCategory record);

    List<AdminResourceCategory> selectByExample(AdminResourceCategoryExample example);

    AdminResourceCategory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AdminResourceCategory record, @Param("example") AdminResourceCategoryExample example);

    int updateByExample(@Param("record") AdminResourceCategory record, @Param("example") AdminResourceCategoryExample example);

    int updateByPrimaryKeySelective(AdminResourceCategory record);

    int updateByPrimaryKey(AdminResourceCategory record);
}