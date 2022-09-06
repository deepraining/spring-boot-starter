package dr.sbs.admin.dao;

import dr.sbs.admin.dto.ArticleRecord;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/** 文章Dao */
public interface ArticleDao {
  /**
   * 获取文章列表
   *
   * @param limit Mysql limit
   * @param offset Mysql offset
   * @param searchKey 搜索关键字
   * @return 文章列表
   */
  List<ArticleRecord> getList(
      @Param("limit") Integer limit,
      @Param("offset") Integer offset,
      @Param("searchKey") String searchKey);

  /**
   * 获取文章列表总数
   *
   * @param searchKey 搜索关键字
   * @return 文章列表总数
   */
  Integer getListTotal(@Param("searchKey") String searchKey);
}
