package senntyou.sbs.jwtdemo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import senntyou.sbs.common.CommonPage;
import senntyou.sbs.common.CommonResult;
import senntyou.sbs.gen.model.User;
import senntyou.sbs.jwtdemo.dto.UserParam;
import senntyou.sbs.jwtdemo.dto.UserQueryParam;
import senntyou.sbs.jwtdemo.service.UserService;

@RestController
@Api(tags = "UserController", description = "User management")
@RequestMapping("/api/user")
public class UserController {
  @Autowired private UserService userService;

  @ApiOperation("Update user")
  @RequestMapping(value = "/update/{uuid}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult update(
      @PathVariable String uuid, @RequestBody UserParam userParam, BindingResult bindingResult) {

    User newUser = userParam.toUser();

    int count = userService.update(uuid, newUser);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("Query list")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<CommonPage<User>> list(
      UserQueryParam userQueryParam,
      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<User> queryList = userService.list(userQueryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.toPage(queryList));
  }

  @ApiOperation("Get a record")
  @RequestMapping(value = "/record/{uuid}", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<User> record(@PathVariable String uuid) {
    User user = userService.getRecord(uuid);
    return CommonResult.success(user);
  }
}
