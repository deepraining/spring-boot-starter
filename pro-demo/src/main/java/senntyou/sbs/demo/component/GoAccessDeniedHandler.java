package senntyou.sbs.demo.component;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class GoAccessDeniedHandler implements AccessDeniedHandler {
  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException, ServletException {
    if (request.getServletPath().startsWith("/api")) {
      // api, return a json result

      response.setHeader("Content-Type", "application/json;charset=utf-8");
      response
          .getWriter()
          .print(
              "{\"code\":401,\"message\":\""
                  + "Not authorized: "
                  + accessDeniedException.getMessage()
                  + "\"}");
      response.getWriter().flush();
    } else {
      // route page, redirect to login page

      response.sendRedirect("account/login");
    }
  }
}
