package senntyou.sbs.jwtdemo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserQueryParam {
  @ApiModelProperty("Username")
  private String username;

  @ApiModelProperty("Email")
  private String email;
}
