package dr.sbs.jwtdemo.dto;

import dr.sbs.mbg.model.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
