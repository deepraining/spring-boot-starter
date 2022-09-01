package dr.sbs.mbg.mapper;

import dr.sbs.mbg.model.ProductAttribute;
import dr.sbs.mbg.model.ProductAttributeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductAttributeMapper {
    long countByExample(ProductAttributeExample example);

    int deleteByExample(ProductAttributeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductAttribute record);

    int insertSelective(ProductAttribute record);

    List<ProductAttribute> selectByExample(ProductAttributeExample example);

    ProductAttribute selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductAttribute record, @Param("example") ProductAttributeExample example);

    int updateByExample(@Param("record") ProductAttribute record, @Param("example") ProductAttributeExample example);

    int updateByPrimaryKeySelective(ProductAttribute record);

    int updateByPrimaryKey(ProductAttribute record);
}