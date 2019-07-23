package senntyou.sbs.jwtdemo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import senntyou.sbs.mbg.model.User;

@Data
public class UserParam {
  /** username */
  @ApiModelProperty(value = "username")
  private String username;

  /** email */
  @ApiModelProperty(value = "email")
  private String email;

  /**
   * convert to User
   *
   * @return
   */
  public User toUser() {
    User user = new User();

    if (this.getUsername() != null) {
      user.setUsername(this.getUsername());
    }
    if (this.getEmail() != null) {
      user.setEmail(this.getEmail());
    }

    return user;
  }
}
