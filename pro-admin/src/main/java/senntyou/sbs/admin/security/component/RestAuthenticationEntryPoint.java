package senntyou.sbs.admin.security.component;

import cn.hutool.json.JSONUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import senntyou.sbs.common.CommonResult;

/** 自定义返回结果：未登录或登录过期 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    response
        .getWriter()
        .println(JSONUtil.parse(CommonResult.unauthorized(authException.getMessage())));
    response.getWriter().flush();
  }
}
