package dr.sbs.search.dao;

import dr.sbs.search.dto.ArticleDoc;
import java.util.List;

public interface ArticleDao {
  List<ArticleDoc> listAll();
}
