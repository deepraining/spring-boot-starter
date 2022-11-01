package dr.sbs.cli.service;

import com.github.pagehelper.PageHelper;
import dr.sbs.mbg.mapper.ArticleMapper;
import dr.sbs.mbg.model.Article;
import dr.sbs.mbg.model.ArticleExample;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ArticleServiceImpl implements ArticleService {
  @Autowired private ArticleMapper articleMapper;

  @Override
  public List<Article> list(String searchKey, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    ArticleExample example = new ArticleExample();
    example.setOrderByClause("id desc");
    ArticleExample.Criteria criteria = example.createCriteria();

    criteria.andStatusEqualTo((byte) 1);
    if (!StringUtils.isEmpty(searchKey)) {
      criteria.andTitleLike("%" + searchKey + "%");
    }
    return articleMapper.selectByExample(example);
  }
}
