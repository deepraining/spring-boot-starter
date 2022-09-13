package dr.sbs.mbg.mapper;

import dr.sbs.mbg.model.WxUser;
import dr.sbs.mbg.model.WxUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WxUserMapper {
    long countByExample(WxUserExample example);

    int deleteByExample(WxUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WxUser record);

    int insertSelective(WxUser record);

    List<WxUser> selectByExample(WxUserExample example);

    WxUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WxUser record, @Param("example") WxUserExample example);

    int updateByExample(@Param("record") WxUser record, @Param("example") WxUserExample example);

    int updateByPrimaryKeySelective(WxUser record);

    int updateByPrimaryKey(WxUser record);
}