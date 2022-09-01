package dr.sbs.admin.controller;

import dr.sbs.admin.dto.AdminPermissionNode;
import dr.sbs.admin.service.AdminPermissionService;
import dr.sbs.common.CommonResult;
import dr.sbs.mbg.model.AdminPermission;
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

/** 后台用户权限管理 */
@Controller
@Api(tags = "AdminPermissionController", description = "后台用户权限管理")
@RequestMapping("/adminPermission")
public class AdminPermissionController {
  @Autowired private AdminPermissionService permissionService;

  @ApiOperation("添加权限")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult create(@RequestBody AdminPermission permission) {
    int count = permissionService.create(permission);
    if (count > 0) {
      return CommonResult.success(count);
    }
    return CommonResult.failed();
  }

  @ApiOperation("修改权限")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult update(@PathVariable Long id, @RequestBody AdminPermission permission) {
    int count = permissionService.update(id, permission);
    if (count > 0) {
      return CommonResult.success(count);
    }
    return CommonResult.failed();
  }

  @ApiOperation("根据id批量删除权限")
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult delete(@RequestParam("ids") List<Long> ids) {
    int count = permissionService.delete(ids);
    if (count > 0) {
      return CommonResult.success(count);
    }
    return CommonResult.failed();
  }

  @ApiOperation("以层级结构返回所有权限")
  @RequestMapping(value = "/treeList", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<List<AdminPermissionNode>> treeList() {
    List<AdminPermissionNode> permissionNodeList = permissionService.treeList();
    return CommonResult.success(permissionNodeList);
  }

  @ApiOperation("获取所有权限列表")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<List<AdminPermission>> list() {
    List<AdminPermission> permissionList = permissionService.list();
    return CommonResult.success(permissionList);
  }
}
