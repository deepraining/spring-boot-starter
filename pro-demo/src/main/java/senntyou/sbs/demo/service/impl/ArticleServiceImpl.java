package senntyou.sbs.demo.service.impl;

import com.github.pagehelper.PageHelper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import senntyou.sbs.demo.dao.ArticleDao;
import senntyou.sbs.demo.dto.ArticleQueryParam;
import senntyou.sbs.demo.service.ArticleService;
import senntyou.sbs.gen.mapper.ArticleMapper;
import senntyou.sbs.gen.model.Article;
import senntyou.sbs.gen.model.ArticleExample;

@Service
public class ArticleServiceImpl implements ArticleService {
  @Autowired private ArticleMapper articleMapper;
  @Autowired private ArticleDao articleDao;

  @Override
  public int create(Article article) {
    article.setId(null);
    articleMapper.insertSelective(article);

    return 1;
  }

  @Override
  public Article getByUuid(String uuid) {
    ArticleExample example = new ArticleExample();
    example.createCriteria().andUuidEqualTo(uuid);
    List<Article> articles = articleMapper.selectByExample(example);

    if (!CollectionUtils.isEmpty(articles)) {
      return articles.get(0);
    }
    return null;
  }

  @Override
  public Article getRecord(String uuid) {
    return articleDao.getRecord(uuid);
  }

  @Override
  public int update(String uuid, Article article) {
    ArticleExample example = new ArticleExample();
    example.createCriteria().andUuidEqualTo(uuid);

    articleMapper.updateByExampleSelective(article, example);

    return 1;
  }

  @Override
  public List<Article> list(
      ArticleQueryParam articleQueryParam, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    ArticleExample example = new ArticleExample();
    ArticleExample.Criteria criteria = example.createCriteria();

    criteria.andDeletedEqualTo(false);
    if (articleQueryParam.getTitle() != null) {
      criteria.andTitleLike("%" + articleQueryParam.getTitle() + "%");
    }
    if (articleQueryParam.getCreateUserUuid() != null) {
      criteria.andCreateUserUuidEqualTo(articleQueryParam.getCreateUserUuid());
    }
    return articleMapper.selectByExample(example);
  }
}
