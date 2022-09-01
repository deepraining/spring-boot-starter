package dr.sbs.mbg.mapper;

import dr.sbs.mbg.model.ProductAttributeCategory;
import dr.sbs.mbg.model.ProductAttributeCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductAttributeCategoryMapper {
    long countByExample(ProductAttributeCategoryExample example);

    int deleteByExample(ProductAttributeCategoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductAttributeCategory record);

    int insertSelective(ProductAttributeCategory record);

    List<ProductAttributeCategory> selectByExample(ProductAttributeCategoryExample example);

    ProductAttributeCategory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductAttributeCategory record, @Param("example") ProductAttributeCategoryExample example);

    int updateByExample(@Param("record") ProductAttributeCategory record, @Param("example") ProductAttributeCategoryExample example);

    int updateByPrimaryKeySelective(ProductAttributeCategory record);

    int updateByPrimaryKey(ProductAttributeCategory record);
}