package dr.sbs.proto.service;

import dr.sbs.mbg.model.Article;
import dr.sbs.proto.dto.ArticleCreateParam;
import java.util.List;

public interface ArticleService {
  List<Article> list(String searchKey, Integer pageSize, Integer pageNum);

  List<Article> myList(Integer pageSize, Integer pageNum);

  int create(ArticleCreateParam articleCreateParam);

  int update(Long id, ArticleCreateParam articleCreateParam);

  int delete(Long id);

  Article getItem(Long id);
}
