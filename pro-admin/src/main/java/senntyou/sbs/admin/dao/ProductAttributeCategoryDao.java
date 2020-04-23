package senntyou.sbs.admin.dao;

import java.util.List;
import senntyou.sbs.admin.dto.ProductAttributeCategoryItem;

/** 自定义商品属性分类Dao Created by macro on 2018/5/24. */
public interface ProductAttributeCategoryDao {
  List<ProductAttributeCategoryItem> getListWithAttr();
}
