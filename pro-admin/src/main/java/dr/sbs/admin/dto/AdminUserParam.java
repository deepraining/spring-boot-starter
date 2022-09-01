package dr.sbs.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/** 用户登录参数 */
@Getter
@Setter
public class AdminUserParam {
  @ApiModelProperty(value = "用户名", required = true)
  @NotEmpty(message = "用户名不能为空")
  private String username;

  @ApiModelProperty(value = "密码", required = true)
  @NotEmpty(message = "密码不能为空")
  private String password;

  @ApiModelProperty(value = "用户头像")
  private String avatar;

  @ApiModelProperty(value = "邮箱")
  @Email(message = "邮箱格式不合法")
  private String email;

  @ApiModelProperty(value = "用户昵称")
  private String nickname;

  @ApiModelProperty(value = "备注")
  private String note;
}
