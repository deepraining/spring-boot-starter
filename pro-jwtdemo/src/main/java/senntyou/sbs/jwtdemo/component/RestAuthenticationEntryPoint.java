package senntyou.sbs.jwtdemo.component;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import senntyou.sbs.common.CommonResult;
import senntyou.sbs.common.util.JsonUtil;

@Component
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
        .println(JsonUtil.objectToJson(CommonResult.unauthorized(authException.getMessage())));
    response.getWriter().flush();
  }
}
