package senntyou.sbs.demo.service;

import java.util.List;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import senntyou.sbs.demo.dto.ArticleQueryParam;
import senntyou.sbs.demo.dto.ArticleResult;
import senntyou.sbs.gen.model.Article;

public interface ArticleService {
  @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
  int create(Article article);

  Article getByUuid(String uuid);

  ArticleResult getRecord(String uuid);

  @Transactional
  int update(String uuid, Article article);

  List<Article> list(ArticleQueryParam articleQueryParam, Integer pageSize, Integer pageNum);
}
