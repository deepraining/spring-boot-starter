package dr.sbs.demo.service.impl;

import com.github.pagehelper.PageHelper;
import dr.sbs.common.util.UuidUtil;
import dr.sbs.demo.dao.ArticleDao;
import dr.sbs.demo.dto.ArticleQueryParam;
import dr.sbs.demo.service.ArticleService;
import dr.sbs.mbg.mapper.ArticleMapper;
import dr.sbs.mbg.model.Article;
import dr.sbs.mbg.model.ArticleExample;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ArticleServiceImpl implements ArticleService {
  @Autowired private ArticleMapper articleMapper;
  @Autowired private ArticleDao articleDao;

  @Override
  public int create(Article article) {
    article.setId(UuidUtil.nextId());
    articleMapper.insertSelective(article);

    return 1;
  }

  @Override
  public Article getById(long id) {
    ArticleExample example = new ArticleExample();
    example.createCriteria().andIdEqualTo(id);
    List<Article> articles = articleMapper.selectByExample(example);

    if (!CollectionUtils.isEmpty(articles)) {
      return articles.get(0);
    }
    return null;
  }

  @Override
  public Article getRecord(long id) {
    return articleDao.getRecord(id);
  }

  @Override
  public int update(long id, Article article) {
    ArticleExample example = new ArticleExample();
    example.createCriteria().andIdEqualTo(id);

    articleMapper.updateByExampleSelective(article, example);

    return 1;
  }

  @Override
  public List<Article> list(
      ArticleQueryParam articleQueryParam, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    ArticleExample example = new ArticleExample();
    ArticleExample.Criteria criteria = example.createCriteria();

    criteria.andDeletedEqualTo(0);
    if (articleQueryParam.getTitle() != null) {
      criteria.andTitleLike("%" + articleQueryParam.getTitle() + "%");
    }
    if (articleQueryParam.getCreateUserId() != null) {
      criteria.andCreateUserIdEqualTo(articleQueryParam.getCreateUserId());
    }
    return articleMapper.selectByExample(example);
  }
}
