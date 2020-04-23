package senntyou.sbs.admin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import senntyou.sbs.mbg.model.ProductAttributeValue;

/** 商品参数，商品自定义规格属性Dao Created by macro on 2018/4/26. */
public interface ProductAttributeValueDao {
  int insertList(@Param("list") List<ProductAttributeValue> productAttributeValueList);
}
