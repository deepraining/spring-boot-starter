package senntyou.sbs.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import senntyou.sbs.mbg.model.Article;

@Data
public class ArticleParam {
  /** title */
  @ApiModelProperty(value = "title")
  private String title;

  /** article summary */
  @ApiModelProperty(value = "article summary")
  private String intro;

  /** article content */
  @ApiModelProperty(value = "article content")
  private String content;

  /**
   * convert to Article
   *
   * @return
   */
  public Article toArticle() {
    Article article = new Article();

    if (this.getTitle() != null) {
      article.setTitle(this.getTitle());
    }
    if (this.getIntro() != null) {
      article.setIntro(this.getIntro());
    }
    if (this.getContent() != null) {
      article.setContent(this.getContent());
    }

    return article;
  }
}
