package dr.sbs.search.repository;

import dr.sbs.search.dto.ArticleDoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleRepository extends ElasticsearchRepository<ArticleDoc, Long> {
  Page<ArticleDoc> findByTitleOrIntro(String title, String intro, Pageable pageable);
}
