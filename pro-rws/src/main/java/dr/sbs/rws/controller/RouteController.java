package dr.sbs.rws.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class RouteController {
  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public String index() {
    return "hello";
  }
}
