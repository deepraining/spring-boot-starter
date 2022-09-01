package dr.sbs.demo.service;

import dr.sbs.demo.dto.ArticleQueryParam;
import dr.sbs.mbg.model.Article;
import java.util.List;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface ArticleService {
  @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
  int create(Article article);

  Article getById(long id);

  Article getRecord(long id);

  @Transactional
  int update(long id, Article article);

  List<Article> list(ArticleQueryParam articleQueryParam, Integer pageSize, Integer pageNum);
}
