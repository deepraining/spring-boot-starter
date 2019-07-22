package senntyou.sbs.gen.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import senntyou.sbs.gen.model.JwtUser;
import senntyou.sbs.gen.model.JwtUserExample;

public interface JwtUserMapper {
    long countByExample(JwtUserExample example);

    int deleteByExample(JwtUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(JwtUser record);

    int insertSelective(JwtUser record);

    List<JwtUser> selectByExample(JwtUserExample example);

    JwtUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") JwtUser record, @Param("example") JwtUserExample example);

    int updateByExample(@Param("record") JwtUser record, @Param("example") JwtUserExample example);

    int updateByPrimaryKeySelective(JwtUser record);

    int updateByPrimaryKey(JwtUser record);
}