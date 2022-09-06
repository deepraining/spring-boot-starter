package dr.sbs.front.component;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class GoAuthenticationFailureHandler implements AuthenticationFailureHandler {
  @Override
  public void onAuthenticationFailure(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {
    response.setHeader("Content-Type", "application/json;charset=utf-8");
    response
        .getWriter()
        .print("{\"code\":1,\"message\":\"" + "Login Failed: " + exception.getMessage() + "\"}");
    response.getWriter().flush();
  }
}
