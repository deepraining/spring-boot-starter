package senntyou.sbs.jwtdemo.service.impl;

import com.github.pagehelper.PageHelper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import senntyou.sbs.gen.mapper.ArticleMapper;
import senntyou.sbs.gen.model.Article;
import senntyou.sbs.gen.model.ArticleExample;
import senntyou.sbs.jwtdemo.dao.ArticleDao;
import senntyou.sbs.jwtdemo.dto.ArticleQueryParam;
import senntyou.sbs.jwtdemo.dto.ArticleResult;
import senntyou.sbs.jwtdemo.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {
  @Autowired private ArticleMapper articleMapper;
  @Autowired private ArticleDao articleDao;

  @Override
  public ArticleResult getRecord(String uuid) {
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
