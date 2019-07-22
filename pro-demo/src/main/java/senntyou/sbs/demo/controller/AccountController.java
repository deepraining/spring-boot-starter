package senntyou.sbs.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import senntyou.sbs.common.CommonResult;
import senntyou.sbs.demo.service.UserService;

@RestController
@Api(tags = "AccountController", description = "Sign up, Login, Password management")
@RequestMapping("/api/account")
public class AccountController {
  @Autowired private UserService userService;

  @ApiOperation("Sign up")
  @RequestMapping(value = "/register", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult register(
      @RequestParam String username, @RequestParam String password, @RequestParam String email) {
    return userService.register(username, password, email);
  }

  @ApiOperation("Update password")
  @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult updatePassword(@RequestParam String username, @RequestParam String password) {
    return userService.updatePassword(username, password);
  }

  @ApiOperation("Current user information")
  @RequestMapping(value = "/currentUser", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult currentUser() {
    return CommonResult.success(userService.getCurrentUser());
  }
}
