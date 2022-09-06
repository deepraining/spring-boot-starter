package dr.sbs.admin.dto;

import dr.sbs.mbg.model.Article;
import dr.sbs.mbg.model.FrontUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleRecord extends Article {
  // position从1000开始，每次增加10，预防中间插入留置
  @ApiModelProperty(value = "前端用户", position = 1000)
  private FrontUser frontUser;
}
