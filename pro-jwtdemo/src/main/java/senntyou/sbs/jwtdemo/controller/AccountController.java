package senntyou.sbs.jwtdemo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import senntyou.sbs.common.CommonResult;
import senntyou.sbs.jwtdemo.service.JwtUserService;

@RestController
@Api(tags = "AccountController", description = "Sign up, Login, Password management")
@RequestMapping("/account")
public class AccountController {
  @Autowired private JwtUserService userService;

  @Value("${jwt.tokenHeader}")
  private String tokenHeader;

  @Value("${jwt.tokenHead}")
  private String tokenHead;

  @ApiOperation("Sign up")
  @RequestMapping(value = "/register", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult register(@RequestParam String username, @RequestParam String password) {
    return userService.register(username, password);
  }

  @ApiOperation(value = "Login")
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult login(@RequestParam String username, @RequestParam String password) {
    String token = userService.login(username, password);
    if (token == null) {
      return CommonResult.validateFailed("Username or password is not correct");
    }
    Map<String, String> tokenMap = new HashMap<>();
    tokenMap.put("token", token);
    tokenMap.put("tokenHead", tokenHead);
    tokenMap.put("tokenHeader", tokenHeader);
    return CommonResult.success(tokenMap);
  }

  @ApiOperation(value = "Refresh token")
  @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult refreshToken(HttpServletRequest request) {
    String token = request.getHeader(tokenHeader);
    String refreshToken = userService.refreshToken(token);
    if (refreshToken == null) {
      return CommonResult.failed();
    }
    Map<String, String> tokenMap = new HashMap<>();
    tokenMap.put("token", refreshToken);
    tokenMap.put("tokenHead", tokenHead);
    tokenMap.put("tokenHeader", tokenHeader);
    return CommonResult.success(tokenMap);
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
