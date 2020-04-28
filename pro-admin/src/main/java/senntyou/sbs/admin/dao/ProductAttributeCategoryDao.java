package senntyou.sbs.admin.dao;

import java.util.List;
import senntyou.sbs.admin.dto.ProductAttributeCategoryItem;

/** 自定义商品属性分类Dao */
public interface ProductAttributeCategoryDao {
  List<ProductAttributeCategoryItem> getListWithAttr();
}
