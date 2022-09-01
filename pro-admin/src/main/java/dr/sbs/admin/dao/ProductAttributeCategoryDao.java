package dr.sbs.admin.dao;

import dr.sbs.admin.dto.ProductAttributeCategoryItem;
import java.util.List;

/** 自定义商品属性分类Dao */
public interface ProductAttributeCategoryDao {
  List<ProductAttributeCategoryItem> getListWithAttr();
}
