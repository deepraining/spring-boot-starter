package senntyou.sbs.demo.dao;

import org.apache.ibatis.annotations.Param;
import senntyou.sbs.gen.model.Article;

public interface ArticleDao {
  Article getRecord(@Param("uuid") String uuid);
}
