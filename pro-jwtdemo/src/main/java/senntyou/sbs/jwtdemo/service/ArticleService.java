package senntyou.sbs.jwtdemo.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import senntyou.sbs.jwtdemo.dto.ArticleQueryParam;
import senntyou.sbs.mbg.model.Article;

public interface ArticleService {
  Article getRecord(long id);

  @Transactional
  int update(long id, Article article);

  List<Article> list(ArticleQueryParam articleQueryParam, Integer pageSize, Integer pageNum);
}
