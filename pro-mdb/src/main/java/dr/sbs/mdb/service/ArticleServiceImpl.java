package dr.sbs.mdb.service;

import dr.sbs.common.util.UuidUtil;
import dr.sbs.mdbmbg.mapper.MdbArticleMapper;
import dr.sbs.mdbmbg.model.MdbArticle;
import dr.sbs.mdbmbg.model.MdbArticleExample;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {
  @Autowired private MdbArticleMapper articleMapper;

  @Override
  public List<MdbArticle> listAll() {
    return articleMapper.selectByExample(new MdbArticleExample());
  }

  @Override
  public int create(MdbArticle article) {
    article.setId(UuidUtil.nextId());
    return articleMapper.insertSelective(article);
  }

  @Override
  public int update(Long id, MdbArticle article) {
    article.setId(id);
    return articleMapper.updateByPrimaryKeySelective(article);
  }

  @Override
  public int delete(Long id) {
    return articleMapper.deleteByPrimaryKey(id);
  }
}
