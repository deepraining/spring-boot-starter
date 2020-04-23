package senntyou.sbs.mbg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import senntyou.sbs.mbg.model.ProductCategoryAttributeRelation;
import senntyou.sbs.mbg.model.ProductCategoryAttributeRelationExample;

public interface ProductCategoryAttributeRelationMapper {
    long countByExample(ProductCategoryAttributeRelationExample example);

    int deleteByExample(ProductCategoryAttributeRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductCategoryAttributeRelation record);

    int insertSelective(ProductCategoryAttributeRelation record);

    List<ProductCategoryAttributeRelation> selectByExample(ProductCategoryAttributeRelationExample example);

    ProductCategoryAttributeRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductCategoryAttributeRelation record, @Param("example") ProductCategoryAttributeRelationExample example);

    int updateByExample(@Param("record") ProductCategoryAttributeRelation record, @Param("example") ProductCategoryAttributeRelationExample example);

    int updateByPrimaryKeySelective(ProductCategoryAttributeRelation record);

    int updateByPrimaryKey(ProductCategoryAttributeRelation record);
}