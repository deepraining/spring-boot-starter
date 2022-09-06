package dr.sbs.front.service;

import com.github.pagehelper.PageHelper;
import dr.sbs.common.exception.ApiAssert;
import dr.sbs.common.util.UuidUtil;
import dr.sbs.front.dto.ArticleCreateParam;
import dr.sbs.mbg.mapper.ArticleMapper;
import dr.sbs.mbg.model.Article;
import dr.sbs.mbg.model.ArticleExample;
import dr.sbs.mbg.model.FrontUser;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ArticleServiceImpl implements ArticleService {
  @Autowired private ArticleMapper articleMapper;
  @Autowired private UserService userService;

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

  @Override
  public List<Article> myList(Integer pageSize, Integer pageNum) {
    FrontUser user = userService.getCurrentUser();

    if (user == null) return new ArrayList<>();

    PageHelper.startPage(pageNum, pageSize);
    ArticleExample example = new ArticleExample();
    example.setOrderByClause("id desc");
    ArticleExample.Criteria criteria = example.createCriteria();

    criteria.andStatusEqualTo((byte) 1);
    criteria.andFrontUserIdEqualTo(user.getId());
    return articleMapper.selectByExample(example);
  }

  @Override
  public int create(ArticleCreateParam articleCreateParam) {
    FrontUser user = userService.getCurrentUser();
    if (user == null) ApiAssert.fail("未登录");

    Article article = new Article();
    BeanUtils.copyProperties(articleCreateParam, article);
    article.setId(UuidUtil.nextId());
    article.setFrontUserId(user.getId());
    return articleMapper.insertSelective(article);
  }

  @Override
  public int update(Long id, ArticleCreateParam articleCreateParam) {
    FrontUser user = userService.getCurrentUser();
    if (user == null) ApiAssert.fail("未登录");

    Article oldArticle = articleMapper.selectByPrimaryKey(id);

    if (oldArticle == null) ApiAssert.fail("文章不存在");
    if (!oldArticle.getFrontUserId().equals(user.getId())) ApiAssert.fail("无权限");

    Article article = new Article();
    BeanUtils.copyProperties(articleCreateParam, article);
    article.setId(id);

    return articleMapper.updateByPrimaryKeySelective(article);
  }

  @Override
  public int delete(Long id) {
    FrontUser user = userService.getCurrentUser();
    if (user == null) ApiAssert.fail("未登录");

    Article oldArticle = articleMapper.selectByPrimaryKey(id);

    if (oldArticle == null) ApiAssert.fail("文章不存在");
    if (!oldArticle.getFrontUserId().equals(user.getId())) ApiAssert.fail("无权限");

    Article article = new Article();
    article.setId(id);
    article.setStatus((byte) 0);

    return articleMapper.updateByPrimaryKeySelective(article);
  }

  @Override
  public Article getItem(Long id) {
    return articleMapper.selectByPrimaryKey(id);
  }
}
