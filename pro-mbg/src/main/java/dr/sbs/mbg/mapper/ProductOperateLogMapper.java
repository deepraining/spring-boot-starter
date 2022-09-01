package dr.sbs.mbg.mapper;

import dr.sbs.mbg.model.ProductOperateLog;
import dr.sbs.mbg.model.ProductOperateLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductOperateLogMapper {
    long countByExample(ProductOperateLogExample example);

    int deleteByExample(ProductOperateLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductOperateLog record);

    int insertSelective(ProductOperateLog record);

    List<ProductOperateLog> selectByExample(ProductOperateLogExample example);

    ProductOperateLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductOperateLog record, @Param("example") ProductOperateLogExample example);

    int updateByExample(@Param("record") ProductOperateLog record, @Param("example") ProductOperateLogExample example);

    int updateByPrimaryKeySelective(ProductOperateLog record);

    int updateByPrimaryKey(ProductOperateLog record);
}