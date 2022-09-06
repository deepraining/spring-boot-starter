package dr.sbs.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/** 文章创建参数 */
@Getter
@Setter
public class ArticleCreateParam {
  // position从10开始，每次增加10，预防中间插入留置
  @ApiModelProperty(value = "标题", required = true, position = 10)
  @NotEmpty(message = "标题不能为空")
  private String title;

  @ApiModelProperty(value = "简介", required = true, position = 20)
  @NotEmpty(message = "简介不能为空")
  private String intro;

  @ApiModelProperty(value = "内容", required = true, position = 30)
  @NotEmpty(message = "内容不能为空")
  private String content;
}
