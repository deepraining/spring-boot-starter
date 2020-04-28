package senntyou.sbs.admin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import senntyou.sbs.admin.dto.ProductAttrInfo;

/** 自定义商品属性Dao */
public interface ProductAttributeDao {
  List<ProductAttrInfo> getProductAttrInfo(@Param("id") Long productCategoryId);
}
