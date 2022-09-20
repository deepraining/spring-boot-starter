package dr.sbs.mdb.controller;

import dr.sbs.common.CommonResult;
import dr.sbs.mbg.model.FrontUser;
import dr.sbs.mdb.service.UserService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired private UserService userService;

  @ApiOperation("所有用户列表")
  @RequestMapping(value = "/listAll", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<List<FrontUser>> listAll() {
    return CommonResult.success(userService.listAll());
  }

  @ApiOperation("添加用户")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> create(@RequestBody FrontUser frontUser) {
    int count = userService.create(frontUser);
    return CommonResult.success(count);
  }

  @ApiOperation("更新用户")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> update(@PathVariable Long id, @RequestBody FrontUser frontUser) {
    int count = userService.update(id, frontUser);
    return CommonResult.success(count);
  }

  @ApiOperation("删除")
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> delete(@PathVariable Long id) {
    int count = userService.delete(id);
    return CommonResult.success(count);
  }
}
