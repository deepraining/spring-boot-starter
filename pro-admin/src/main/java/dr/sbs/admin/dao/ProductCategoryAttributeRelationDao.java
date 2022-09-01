package dr.sbs.admin.dao;

import dr.sbs.mbg.model.ProductCategoryAttributeRelation;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/** 自定义商品分类和属性关系Dao */
public interface ProductCategoryAttributeRelationDao {
  int insertList(
      @Param("list") List<ProductCategoryAttributeRelation> productCategoryAttributeRelationList);
}
