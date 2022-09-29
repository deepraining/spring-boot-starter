package dr.sbs.front.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/** 用户创建参数 */
@Getter
@Setter
public class UserCreateParam {
  // position从10开始，每次增加10，预防中间插入留置
  @ApiModelProperty(value = "用户名", required = true, position = 10)
  @NotEmpty(message = "用户名不能为空")
  private String username;

  @ApiModelProperty(value = "电子邮箱", required = true, position = 20)
  @NotEmpty(message = "电子邮箱不能为空")
  private String email;

  @ApiModelProperty(value = "密码", required = true, position = 30)
  @NotEmpty(message = "密码不能为空")
  private String password;
}
