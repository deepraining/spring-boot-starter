package senntyou.sbs.admin.dao;

import org.apache.ibatis.annotations.Param;
import senntyou.sbs.admin.dto.ProductResult;

/** 商品自定义Dao */
public interface ProductDao {
  /** 获取商品编辑信息 */
  ProductResult getUpdateInfo(@Param("id") Long id);
}
