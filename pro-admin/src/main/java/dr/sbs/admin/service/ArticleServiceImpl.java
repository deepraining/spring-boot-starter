package dr.sbs.admin.service;

import com.github.pagehelper.PageInfo;
import dr.sbs.admin.dao.ArticleDao;
import dr.sbs.admin.dto.ArticleCreateParam;
import dr.sbs.admin.dto.ArticleRecord;
import dr.sbs.common.util.UuidUtil;
import dr.sbs.mbg.mapper.ArticleMapper;
import dr.sbs.mbg.model.Article;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 后台文章管理Service实现类 */
@Service
public class ArticleServiceImpl implements ArticleService {
  @Autowired private ArticleMapper articleMapper;
  @Autowired private ArticleDao articleDao;

  @Override
  public PageInfo<ArticleRecord> list(String searchKey, Integer pageSize, Integer pageNum) {
    int limit = pageSize;
    int offset = (pageNum - 1) * pageSize;

    List<ArticleRecord> list = articleDao.getList(limit, offset, searchKey);
    Integer total = articleDao.getListTotal(searchKey);

    PageInfo<ArticleRecord> pageInfo = new PageInfo<>(list);
    pageInfo.setTotal(total);
    pageInfo.setPageNum(pageNum);
    pageInfo.setPageSize(pageSize);
    return pageInfo;
  }

  @Override
  public int create(ArticleCreateParam articleCreateParam) {
    Article article = new Article();
    BeanUtils.copyProperties(articleCreateParam, article);
    article.setId(UuidUtil.nextId());
    article.setFrontUserId(0L);
    return articleMapper.insertSelective(article);
  }

  @Override
  public int update(Long id, ArticleCreateParam articleCreateParam) {
    Article article = new Article();
    BeanUtils.copyProperties(articleCreateParam, article);
    article.setId(id);
    return articleMapper.updateByPrimaryKeySelective(article);
  }

  @Override
  public int delete(Long id) {
    Article article = new Article();
    article.setId(id);
    article.setStatus((byte) 0);
    return articleMapper.updateByPrimaryKeySelective(article);
  }
}
