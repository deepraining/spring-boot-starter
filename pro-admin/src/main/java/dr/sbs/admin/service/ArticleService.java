package dr.sbs.admin.service;

import com.github.pagehelper.PageInfo;
import dr.sbs.admin.dto.ArticleCreateParam;
import dr.sbs.admin.dto.ArticleRecord;

/** 文章管理Service */
public interface ArticleService {
  /** 获取文章列表 */
  PageInfo<ArticleRecord> list(String searchKey, Integer pageSize, Integer pageNum);

  /** 创建文章 */
  int create(ArticleCreateParam articleCreateParam);

  /** 修改文章 */
  int update(Long id, ArticleCreateParam articleCreateParam);

  /** 删除文章 */
  int delete(Long id);
}
