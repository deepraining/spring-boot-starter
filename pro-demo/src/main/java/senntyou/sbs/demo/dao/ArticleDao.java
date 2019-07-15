package senntyou.sbs.demo.dao;

import org.apache.ibatis.annotations.Param;
import senntyou.sbs.demo.dto.ArticleResult;

public interface ArticleDao {
  ArticleResult getRecord(@Param("uuid") String uuid);
}
