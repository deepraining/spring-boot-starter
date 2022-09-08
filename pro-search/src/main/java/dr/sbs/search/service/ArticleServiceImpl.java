package dr.sbs.search.service;

import dr.sbs.mbg.mapper.ArticleMapper;
import dr.sbs.mbg.model.Article;
import dr.sbs.search.dao.ArticleDao;
import dr.sbs.search.dto.ArticleDoc;
import dr.sbs.search.repository.ArticleRepository;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {
  @Autowired private ArticleRepository articleRepository;
  @Autowired private ArticleDao articleDao;
  @Autowired private ArticleMapper articleMapper;

  @Override
  public int importAll() {
    List<ArticleDoc> list = articleDao.listAll();
    Iterable<ArticleDoc> resultIterable = articleRepository.saveAll(list);
    Iterator<ArticleDoc> resultIterator = resultIterable.iterator();
    int result = 0;
    while (resultIterator.hasNext()) {
      result++;
      resultIterator.next();
    }
    return result;
  }

  @Override
  public int create(Long id) {
    Article article = articleMapper.selectByPrimaryKey(id);
    if (article == null) return 0;
    ArticleDoc articleDoc = new ArticleDoc();
    BeanUtils.copyProperties(article, articleDoc);
    articleRepository.save(articleDoc);
    return 1;
  }

  @Override
  public int delete(Long id) {
    articleRepository.deleteById(id);
    return 1;
  }

  @Override
  public int batchDelete(List<Long> ids) {
    List<ArticleDoc> list = new ArrayList<>();
    for (Long id : ids) {
      ArticleDoc articleDoc = new ArticleDoc();
      articleDoc.setId(id);
      list.add(articleDoc);
    }
    articleRepository.deleteAll(list);
    return ids.size();
  }

  @Override
  public Page<ArticleDoc> search(String searchKey, Integer pageSize, Integer pageNum) {
    // es pageNum 从0开始
    Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
    return articleRepository.findByTitleOrIntro(searchKey, searchKey, pageable);
  }
}
