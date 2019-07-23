package senntyou.sbs.demo.dao;

import org.apache.ibatis.annotations.Param;
import senntyou.sbs.mbg.model.Article;

public interface ArticleDao {
  Article getRecord(@Param("uuid") String uuid);
}
