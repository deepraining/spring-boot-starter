package dr.sbs.admin.dao;

import dr.sbs.admin.dto.ProductResult;
import org.apache.ibatis.annotations.Param;

/** 商品自定义Dao */
public interface ProductDao {
  /** 获取商品编辑信息 */
  ProductResult getUpdateInfo(@Param("id") Long id);
}
