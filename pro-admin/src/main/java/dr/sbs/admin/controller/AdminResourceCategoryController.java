package dr.sbs.admin.controller;

import dr.sbs.admin.service.AdminResourceCategoryService;
import dr.sbs.common.CommonResult;
import dr.sbs.mbg.model.AdminResourceCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/** 后台资源分类管理 */
@Controller
@Api(tags = "AdminResourceCategoryController", description = "后台资源分类管理")
@RequestMapping("/adminResourceCategory")
public class AdminResourceCategoryController {
  @Autowired private AdminResourceCategoryService resourceCategoryService;

  @ApiOperation("查询所有后台资源分类")
  @RequestMapping(value = "/listAll", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<List<AdminResourceCategory>> listAll() {
    List<AdminResourceCategory> resourceList = resourceCategoryService.listAll();
    return CommonResult.success(resourceList);
  }

  @ApiOperation("添加后台资源分类")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> create(
      @RequestBody @Validated AdminResourceCategory adminResourceCategory,
      BindingResult bindingResult) {
    int count = resourceCategoryService.create(adminResourceCategory);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("修改后台资源分类")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> update(
      @PathVariable Long id,
      @RequestBody @Validated AdminResourceCategory adminResourceCategory,
      BindingResult bindingResult) {
    int count = resourceCategoryService.update(id, adminResourceCategory);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("根据ID删除后台资源")
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> delete(@PathVariable Long id) {
    int count = resourceCategoryService.delete(id);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }
}
