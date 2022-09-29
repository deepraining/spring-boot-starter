package dr.sbs.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class RouteController {
  @RequestMapping(value = "", method = RequestMethod.GET)
  public String index() {
    return "index";
  }

  @RequestMapping(value = "account/login", method = RequestMethod.GET)
  public String accountLogin() {
    return "account/login";
  }

  @RequestMapping(value = "account/register", method = RequestMethod.GET)
  public String accountRegister() {
    return "account/register";
  }

  @RequestMapping(value = "account/updatePassword", method = RequestMethod.GET)
  public String accountUpdatePassword() {
    return "account/updatePassword";
  }

  @RequestMapping(value = "user", method = RequestMethod.GET)
  public String user() {
    return "user/index";
  }

  @RequestMapping(value = "user/chat", method = RequestMethod.GET)
  public String userChat() {
    return "user/chat";
  }

  @RequestMapping(value = "article/create", method = RequestMethod.GET)
  public String articleCreate() {
    return "article/create";
  }

  @RequestMapping(value = "article/update/{id}", method = RequestMethod.GET)
  public String articleUpdate() {
    return "article/update";
  }

  @RequestMapping(value = "article/record/{id}", method = RequestMethod.GET)
  public String articleRecord() {
    return "article/record";
  }
}
