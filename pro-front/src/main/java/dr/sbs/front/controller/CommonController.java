package dr.sbs.front.controller;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class CommonController implements ErrorController {
  private static final String ERROR_PATH = "/error";

  @GetMapping(value = ERROR_PATH)
  public String handleError(HttpServletRequest request) {
    final Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

    log.error("Error path: [{}], status: [{}]", getErrorPath(), statusCode);

    if (statusCode == 500) {
      return "redirect:/500";
    } else {
      return "redirect:/404";
    }
  }

  @GetMapping(value = "/404")
  public String notFround() {
    return "error/404";
  }

  @GetMapping(value = "/500")
  public String internalError() {
    return "error/500";
  }

  @Override
  public String getErrorPath() {
    return ERROR_PATH;
  }
}
