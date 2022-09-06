package dr.sbs.admin.controller;

import dr.sbs.admin.component.DynamicSecurityMetadataSource;
import dr.sbs.admin.service.AdminResourceService;
import dr.sbs.common.CommonPage;
import dr.sbs.common.CommonResult;
import dr.sbs.mbg.model.AdminResource;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/** 后台资源管理Controller */
@Controller
@Api(tags = "AdminResourceController", description = "后台资源管理")
@RequestMapping("/adminResource")
public class AdminResourceController {
  @Autowired private AdminResourceService resourceService;
  @Autowired private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

  @ApiOperation("添加后台资源")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> create(
      @RequestBody @Validated AdminResource adminResource, BindingResult bindingResult) {
    int count = resourceService.create(adminResource);
    dynamicSecurityMetadataSource.clearDataSource();
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("修改后台资源")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> update(
      @PathVariable Long id,
      @RequestBody @Validated AdminResource adminResource,
      BindingResult bindingResult) {
    int count = resourceService.update(id, adminResource);
    dynamicSecurityMetadataSource.clearDataSource();
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("根据ID获取资源详情")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<AdminResource> getItem(@PathVariable Long id) {
    AdminResource adminResource = resourceService.getItem(id);
    return CommonResult.success(adminResource);
  }

  @ApiOperation("根据ID删除后台资源")
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> delete(@PathVariable Long id) {
    int count = resourceService.delete(id);
    dynamicSecurityMetadataSource.clearDataSource();
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("分页模糊查询后台资源")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<CommonPage<AdminResource>> list(
      @RequestParam(required = false) Long categoryId,
      @RequestParam(required = false) String nameKeyword,
      @RequestParam(required = false) String urlKeyword,
      @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<AdminResource> resourceList =
        resourceService.list(categoryId, nameKeyword, urlKeyword, pageSize, pageNum);
    return CommonResult.success(CommonPage.toPage(resourceList));
  }

  @ApiOperation("查询所有后台资源")
  @RequestMapping(value = "/listAll", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<List<AdminResource>> listAll() {
    List<AdminResource> resourceList = resourceService.listAll();
    return CommonResult.success(resourceList);
  }
}
