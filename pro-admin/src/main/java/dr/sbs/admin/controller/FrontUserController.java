package dr.sbs.admin.controller;

import dr.sbs.admin.dto.FrontUserCreateParam;
import dr.sbs.admin.service.FrontUserService;
import dr.sbs.common.CommonPage;
import dr.sbs.common.CommonResult;
import dr.sbs.mbg.model.FrontUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/** 前端用户管理 */
@Controller
@Api(tags = "FrontUserController", description = "前端用户管理")
@RequestMapping("/frontUser")
public class FrontUserController {
  @Autowired private FrontUserService frontUserService;

  @ApiOperation("查询前端用户列表")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<CommonPage<FrontUser>> list(
      @RequestParam(value = "pageSize", defaultValue = "10")
          @ApiParam(value = "每页条数", defaultValue = "10")
          Integer pageSize,
      @RequestParam(value = "pageNum", defaultValue = "1")
          @ApiParam(value = "页码", defaultValue = "1")
          Integer pageNum,
      @RequestParam(value = "searchKey", defaultValue = "")
          @ApiParam(value = "搜索关键字", defaultValue = "")
          String searchKey) {
    List<FrontUser> frontUserList = frontUserService.list(searchKey, pageSize, pageNum);
    return CommonResult.success(CommonPage.toPage(frontUserList));
  }

  @ApiOperation("添加前端用户")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> create(
      @RequestBody @Validated FrontUserCreateParam frontUserCreateParam,
      BindingResult bindingResult) {
    int count = frontUserService.create(frontUserCreateParam);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("修改前端用户")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> update(
      @PathVariable Long id,
      @RequestBody @Validated FrontUserCreateParam frontUserCreateParam,
      BindingResult bindingResult) {
    int count = frontUserService.update(id, frontUserCreateParam);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("根据ID删除前端用户")
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> delete(@PathVariable Long id) {
    int count = frontUserService.delete(id);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }
}
