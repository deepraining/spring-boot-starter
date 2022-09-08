package dr.sbs.search.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@EqualsAndHashCode
@Document(indexName = "article", shards = 1, replicas = 0)
public class ArticleDoc implements Serializable {
  private static final long serialVersionUID = -1L;

  @Id private Long id;

  @Field(analyzer = "ik_max_word", type = FieldType.Text)
  private String title;

  @Field(analyzer = "ik_max_word", type = FieldType.Text)
  private String intro;

  private Integer readCount;

  private Integer supportCount;
}
