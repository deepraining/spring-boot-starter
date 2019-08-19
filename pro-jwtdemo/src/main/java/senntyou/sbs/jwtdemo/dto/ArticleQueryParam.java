package senntyou.sbs.jwtdemo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ArticleQueryParam {
  @ApiModelProperty("Title")
  private String title;

  @ApiModelProperty("Creator userId")
  private Long createUserId;
}
