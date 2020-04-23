package senntyou.sbs.admin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import senntyou.sbs.mbg.model.ProductVerifyRecord;

/** 商品审核日志自定义Dao Created by macro on 2018/4/27. */
public interface ProductVerifyRecordDao {
  int insertList(@Param("list") List<ProductVerifyRecord> list);
}
