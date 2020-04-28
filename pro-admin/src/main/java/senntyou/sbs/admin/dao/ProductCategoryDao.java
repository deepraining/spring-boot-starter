package senntyou.sbs.admin.dao;

import java.util.List;
import senntyou.sbs.admin.dto.ProductCategoryWithChildrenItem;

/** 商品分类自定义Dao */
public interface ProductCategoryDao {
  List<ProductCategoryWithChildrenItem> listWithChildren();
}
