package dr.sbs.admin.dao;

import dr.sbs.admin.dto.ProductCategoryWithChildrenItem;
import java.util.List;

/** 商品分类自定义Dao */
public interface ProductCategoryDao {
  List<ProductCategoryWithChildrenItem> listWithChildren();
}
