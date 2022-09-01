package dr.sbs.jwtdemo.service;

import dr.sbs.jwtdemo.dto.ArticleQueryParam;
import dr.sbs.mbg.model.Article;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface ArticleService {
  Article getRecord(long id);

  @Transactional
  int update(long id, Article article);

  List<Article> list(ArticleQueryParam articleQueryParam, Integer pageSize, Integer pageNum);
}
