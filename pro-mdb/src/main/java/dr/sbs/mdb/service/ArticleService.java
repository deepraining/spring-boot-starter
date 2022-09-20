package dr.sbs.mdb.service;

import dr.sbs.mdbmbg.model.MdbArticle;
import java.util.List;

public interface ArticleService {
  // 所有文章列表
  List<MdbArticle> listAll();

  // 添加文章
  int create(MdbArticle article);

  // 更新文章
  int update(Long id, MdbArticle article);

  // 删除文章
  int delete(Long id);
}
