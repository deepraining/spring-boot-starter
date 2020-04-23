package senntyou.sbs.admin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import senntyou.sbs.mbg.model.ProductCategoryAttributeRelation;

/** 自定义商品分类和属性关系Dao Created by macro on 2018/5/23. */
public interface ProductCategoryAttributeRelationDao {
  int insertList(
      @Param("list") List<ProductCategoryAttributeRelation> productCategoryAttributeRelationList);
}
