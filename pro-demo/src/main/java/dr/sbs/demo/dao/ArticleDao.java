package dr.sbs.demo.dao;

import dr.sbs.mbg.model.Article;
import org.apache.ibatis.annotations.Param;

public interface ArticleDao {
  Article getRecord(@Param("id") long id);
}
