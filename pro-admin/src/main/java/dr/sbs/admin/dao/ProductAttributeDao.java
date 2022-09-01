package dr.sbs.admin.dao;

import dr.sbs.admin.dto.ProductAttrInfo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/** 自定义商品属性Dao */
public interface ProductAttributeDao {
  List<ProductAttrInfo> getProductAttrInfo(@Param("id") Long productCategoryId);
}
