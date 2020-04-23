package senntyou.sbs.admin.dao;

import java.util.List;
import senntyou.sbs.admin.dto.ProductCategoryWithChildrenItem;

/** 商品分类自定义Dao Created by macro on 2018/5/25. */
public interface ProductCategoryDao {
  List<ProductCategoryWithChildrenItem> listWithChildren();
}
