package dr.sbs.front.component;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class GoAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {
    if (request.getServletPath().startsWith("/api")) {
      // api, return a json result

      response.setHeader("Content-Type", "application/json;charset=utf-8");
      response
          .getWriter()
          .print(
              "{\"code\":3,\"message\":\""
                  + "No privileges: "
                  + authException.getMessage()
                  + "\"}");
      response.getWriter().flush();
    } else {
      // route page, redirect to login page

      response.sendRedirect("account/login");
    }
  }
}
