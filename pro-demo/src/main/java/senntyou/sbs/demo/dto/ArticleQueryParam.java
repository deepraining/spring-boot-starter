package senntyou.sbs.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ArticleQueryParam {
  @ApiModelProperty("Title")
  private String title;

  @ApiModelProperty("Creator userUuid")
  private String createUserUuid;
}
