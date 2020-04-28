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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import senntyou.sbs.admin.security.component.DynamicSecurityMetadataSource;
import senntyou.sbs.admin.service.AdminResourceService;
import senntyou.sbs.common.CommonPage;
import senntyou.sbs.common.CommonResult;
import senntyou.sbs.mbg.model.AdminResource;

/** 后台资源管理Controller */
@Controller
@Api(tags = "AdminResourceController", description = "后台资源管理")
@RequestMapping("/resource")
public class ResourceController {

  @Autowired private AdminResourceService resourceService;
  @Autowired private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

  @ApiOperation("添加后台资源")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult create(@RequestBody AdminResource adminResource) {
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
  public CommonResult update(@PathVariable Long id, @RequestBody AdminResource adminResource) {
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
  public CommonResult delete(@PathVariable Long id) {
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
