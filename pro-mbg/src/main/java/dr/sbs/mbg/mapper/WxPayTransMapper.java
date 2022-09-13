package dr.sbs.mbg.mapper;

import dr.sbs.mbg.model.WxPayTrans;
import dr.sbs.mbg.model.WxPayTransExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WxPayTransMapper {
    long countByExample(WxPayTransExample example);

    int deleteByExample(WxPayTransExample example);

    int deleteByPrimaryKey(String transId);

    int insert(WxPayTrans record);

    int insertSelective(WxPayTrans record);

    List<WxPayTrans> selectByExample(WxPayTransExample example);

    WxPayTrans selectByPrimaryKey(String transId);

    int updateByExampleSelective(@Param("record") WxPayTrans record, @Param("example") WxPayTransExample example);

    int updateByExample(@Param("record") WxPayTrans record, @Param("example") WxPayTransExample example);

    int updateByPrimaryKeySelective(WxPayTrans record);

    int updateByPrimaryKey(WxPayTrans record);
}