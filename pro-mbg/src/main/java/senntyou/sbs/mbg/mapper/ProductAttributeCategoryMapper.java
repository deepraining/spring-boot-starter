package senntyou.sbs.mbg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import senntyou.sbs.mbg.model.ProductAttributeCategory;
import senntyou.sbs.mbg.model.ProductAttributeCategoryExample;

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