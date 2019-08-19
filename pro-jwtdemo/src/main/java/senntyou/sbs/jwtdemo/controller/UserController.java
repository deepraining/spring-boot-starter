package senntyou.sbs.jwtdemo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import senntyou.sbs.common.CommonPage;
import senntyou.sbs.common.CommonResult;
import senntyou.sbs.jwtdemo.dto.UserParam;
import senntyou.sbs.jwtdemo.dto.UserQueryParam;
import senntyou.sbs.jwtdemo.service.UserService;
import senntyou.sbs.mbg.model.User;

@RestController
@Api(tags = "UserController", description = "User management")
@RequestMapping("/user")
public class UserController {
  @Autowired private UserService userService;

  @ApiOperation("Update user")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult update(@PathVariable long id, @RequestBody UserParam userParam) {

    User newUser = userParam.toUser();

    int count = userService.update(id, newUser);
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
  @RequestMapping(value = "/record/{id}", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<User> record(@PathVariable long id) {
    User user = userService.getRecord(id);
    return CommonResult.success(user);
  }

  @ApiOperation("Delete user")
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult delete(@PathVariable long id) {

    User user = new User();
    user.setDeleted(1);

    int count = userService.update(id, user);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }
}
