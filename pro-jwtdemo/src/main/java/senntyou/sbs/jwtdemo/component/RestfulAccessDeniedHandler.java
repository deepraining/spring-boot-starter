package senntyou.sbs.jwtdemo.component;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import senntyou.sbs.common.CommonResult;
import senntyou.sbs.common.util.JsonUtil;

@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
  @Override
  public void handle(
      HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
      throws IOException, ServletException {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    response.getWriter().println(JsonUtil.objectToJson(CommonResult.forbidden(e.getMessage())));
    response.getWriter().flush();
  }
}
