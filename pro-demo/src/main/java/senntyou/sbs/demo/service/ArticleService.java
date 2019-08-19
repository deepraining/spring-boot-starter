package senntyou.sbs.demo.service;

import java.util.List;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import senntyou.sbs.demo.dto.ArticleQueryParam;
import senntyou.sbs.mbg.model.Article;

public interface ArticleService {
  @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
  int create(Article article);

  Article getById(long id);

  Article getRecord(long id);

  @Transactional
  int update(long id, Article article);

  List<Article> list(ArticleQueryParam articleQueryParam, Integer pageSize, Integer pageNum);
}
