package senntyou.sbs.mbg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import senntyou.sbs.mbg.model.ProductVerifyRecord;
import senntyou.sbs.mbg.model.ProductVerifyRecordExample;

public interface ProductVerifyRecordMapper {
    long countByExample(ProductVerifyRecordExample example);

    int deleteByExample(ProductVerifyRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductVerifyRecord record);

    int insertSelective(ProductVerifyRecord record);

    List<ProductVerifyRecord> selectByExample(ProductVerifyRecordExample example);

    ProductVerifyRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductVerifyRecord record, @Param("example") ProductVerifyRecordExample example);

    int updateByExample(@Param("record") ProductVerifyRecord record, @Param("example") ProductVerifyRecordExample example);

    int updateByPrimaryKeySelective(ProductVerifyRecord record);

    int updateByPrimaryKey(ProductVerifyRecord record);
}