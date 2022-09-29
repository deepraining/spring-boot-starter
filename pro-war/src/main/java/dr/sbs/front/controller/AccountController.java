package dr.sbs.front.controller;

import dr.sbs.common.CommonResult;
import dr.sbs.front.dto.UpdatePasswordParam;
import dr.sbs.front.dto.UserCreateParam;
import dr.sbs.front.service.UserService;
import dr.sbs.mbg.model.FrontUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "AccountController", description = "Sign up, Login, Password management")
@RequestMapping("/api/account")
public class AccountController {
  @Autowired private UserService userService;

  @ApiOperation("Sign up")
  @RequestMapping(value = "/register", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<FrontUser> register(
      @RequestBody @Validated UserCreateParam userCreateParam, BindingResult bindingResult) {
    FrontUser user = userService.register(userCreateParam);
    if (user != null) return CommonResult.success(user);
    return CommonResult.failed();
  }

  @ApiOperation("Update password")
  @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> updatePassword(
      @RequestBody @Validated UpdatePasswordParam updatePasswordParam,
      BindingResult bindingResult) {
    int count = userService.updatePassword(updatePasswordParam);
    if (count > 0) return CommonResult.success(count);
    return CommonResult.failed();
  }

  @ApiOperation("Current user information")
  @RequestMapping(value = "/currentUser", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<FrontUser> currentUser() {
    FrontUser user = userService.getCurrentUser();
    if (user != null) return CommonResult.success(user);
    return CommonResult.failed();
  }
}
