package dr.sbs.search.service;

import dr.sbs.search.dto.ArticleDoc;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ArticleService {
  // 从数据库中导入所有文章
  int importAll();

  // 添加文章
  int create(Long id);

  // 删除文章
  int delete(Long id);

  // 批量删除文章
  int batchDelete(List<Long> ids);

  // 搜索
  Page<ArticleDoc> search(String searchKey, Integer pageSize, Integer pageNum);
}
