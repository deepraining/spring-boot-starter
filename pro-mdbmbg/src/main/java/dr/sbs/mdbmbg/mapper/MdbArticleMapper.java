package dr.sbs.mdbmbg.mapper;

import dr.sbs.mdbmbg.model.MdbArticle;
import dr.sbs.mdbmbg.model.MdbArticleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MdbArticleMapper {
  long countByExample(MdbArticleExample example);

  int deleteByExample(MdbArticleExample example);

  int deleteByPrimaryKey(Long id);

  int insert(MdbArticle record);

  int insertSelective(MdbArticle record);

  List<MdbArticle> selectByExampleWithBLOBs(MdbArticleExample example);

  List<MdbArticle> selectByExample(MdbArticleExample example);

  MdbArticle selectByPrimaryKey(Long id);

  int updateByExampleSelective(
      @Param("record") MdbArticle record, @Param("example") MdbArticleExample example);

  int updateByExampleWithBLOBs(
      @Param("record") MdbArticle record, @Param("example") MdbArticleExample example);

  int updateByExample(
      @Param("record") MdbArticle record, @Param("example") MdbArticleExample example);

  int updateByPrimaryKeySelective(MdbArticle record);

  int updateByPrimaryKeyWithBLOBs(MdbArticle record);

  int updateByPrimaryKey(MdbArticle record);
}
