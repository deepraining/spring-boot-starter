package senntyou.sbs.jwtdemo.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import senntyou.sbs.gen.model.Article;
import senntyou.sbs.jwtdemo.dto.ArticleQueryParam;

public interface ArticleService {
  Article getRecord(String uuid);

  @Transactional
  int update(String uuid, Article article);

  List<Article> list(ArticleQueryParam articleQueryParam, Integer pageSize, Integer pageNum);
}
