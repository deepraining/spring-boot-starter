package senntyou.sbs.jwtdemo.dao;

import org.apache.ibatis.annotations.Param;
import senntyou.sbs.gen.model.Article;

public interface ArticleDao {
  Article getRecord(@Param("uuid") String uuid);
}
