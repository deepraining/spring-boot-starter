package senntyou.sbs.jwtdemo.dao;

import org.apache.ibatis.annotations.Param;
import senntyou.sbs.jwtdemo.dto.ArticleResult;

public interface ArticleDao {
  ArticleResult getRecord(@Param("uuid") String uuid);
}
