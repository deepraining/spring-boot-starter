package dr.sbs.admin.dao;

import dr.sbs.mbg.model.ProductVerifyRecord;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/** 商品审核日志自定义Dao */
public interface ProductVerifyRecordDao {
  int insertList(@Param("list") List<ProductVerifyRecord> list);
}
