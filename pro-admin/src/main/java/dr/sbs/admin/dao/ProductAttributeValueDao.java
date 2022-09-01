package dr.sbs.admin.dao;

import dr.sbs.mbg.model.ProductAttributeValue;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/** 商品参数，商品自定义规格属性Dao */
public interface ProductAttributeValueDao {
  int insertList(@Param("list") List<ProductAttributeValue> productAttributeValueList);
}
