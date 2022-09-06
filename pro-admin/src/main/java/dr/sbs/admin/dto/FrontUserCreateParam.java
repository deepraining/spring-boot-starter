package dr.sbs.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/** 文章创建参数 */
@Getter
@Setter
public class FrontUserCreateParam {
  // position从10开始，每次增加10，预防中间插入留置
  @ApiModelProperty(value = "用户名", required = true, position = 10)
  @NotEmpty(message = "用户名不能为空")
  private String username;

  @ApiModelProperty(value = "电子邮箱", required = true, position = 20)
  @NotEmpty(message = "电子邮箱不能为空")
  private String email;
}
