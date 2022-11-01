package dr.sbs.cli.service;

import dr.sbs.mbg.model.Article;
import java.util.List;

public interface ArticleService {
  List<Article> list(String searchKey, Integer pageSize, Integer pageNum);
}
