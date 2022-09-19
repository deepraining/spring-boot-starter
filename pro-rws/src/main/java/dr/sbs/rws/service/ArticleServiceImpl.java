package dr.sbs.rws.service;

import dr.sbs.common.util.UuidUtil;
import dr.sbs.mbg.mapper.ArticleMapper;
import dr.sbs.mbg.model.Article;
import dr.sbs.mbg.model.ArticleExample;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {
  @Autowired private ArticleMapper articleMapper;

  @Override
  public List<Article> listAll() {
    return articleMapper.selectByExample(new ArticleExample());
  }

  @Override
  public int create(Article article) {
    article.setId(UuidUtil.nextId());
    return articleMapper.insertSelective(article);
  }

  @Override
  public int update(Long id, Article article) {
    article.setId(id);
    return articleMapper.updateByPrimaryKeySelective(article);
  }

  @Override
  public int delete(Long id) {
    return articleMapper.deleteByPrimaryKey(id);
  }
}
