package dr.sbs.front.service;

import dr.sbs.front.dto.ArticleCreateParam;
import dr.sbs.mbg.model.Article;
import java.util.List;

public interface ArticleService {
  List<Article> list(String searchKey, Integer pageSize, Integer pageNum);

  List<Article> myList(Integer pageSize, Integer pageNum);

  int create(ArticleCreateParam articleCreateParam);

  int update(Long id, ArticleCreateParam articleCreateParam);

  int delete(Long id);

  Article getItem(Long id);
}
