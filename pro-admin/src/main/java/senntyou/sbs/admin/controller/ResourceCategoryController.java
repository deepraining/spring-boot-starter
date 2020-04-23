package senntyou.sbs.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import senntyou.sbs.admin.service.AdminResourceCategoryService;
import senntyou.sbs.common.CommonResult;
import senntyou.sbs.mbg.model.AdminResourceCategory;

/** 后台资源分类管理Controller Created by macro on 2020/2/5. */
@Controller
@Api(tags = "AdminResourceCategoryController", description = "后台资源分类管理")
@RequestMapping("/resourceCategory")
public class ResourceCategoryController {
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
  public CommonResult create(@RequestBody AdminResourceCategory umsResourceCategory) {
    int count = resourceCategoryService.create(umsResourceCategory);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("修改后台资源分类")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult update(
      @PathVariable Long id, @RequestBody AdminResourceCategory umsResourceCategory) {
    int count = resourceCategoryService.update(id, umsResourceCategory);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("根据ID删除后台资源")
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult delete(@PathVariable Long id) {
    int count = resourceCategoryService.delete(id);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }
}
