package dr.sbs.admin.controller;

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
}
