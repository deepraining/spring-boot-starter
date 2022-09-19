package dr.sbs.rws.service;

import dr.sbs.mbg.model.Article;
import java.util.List;

public interface ArticleService {
  // 所有文章列表
  List<Article> listAll();

  // 添加文章
  int create(Article article);

  // 更新文章
  int update(Long id, Article article);

  // 删除文章
  int delete(Long id);
}
