package dr.sbs.admin.controller;

import dr.sbs.admin.dto.AdminMenuNode;
import dr.sbs.admin.service.AdminMenuService;
import dr.sbs.common.CommonPage;
import dr.sbs.common.CommonResult;
import dr.sbs.mbg.model.AdminMenu;
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

/** 后台菜单管理 */
@Controller
@Api(tags = "AdminMenuController", description = "后台菜单管理")
@RequestMapping("/adminMenu")
public class AdminMenuController {
  @Autowired private AdminMenuService menuService;

  @ApiOperation("添加后台菜单")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> create(
      @RequestBody @Validated AdminMenu adminMenu, BindingResult bindingResult) {
    int count = menuService.create(adminMenu);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("修改后台菜单")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> update(
      @PathVariable Long id,
      @RequestBody @Validated AdminMenu adminMenu,
      BindingResult bindingResult) {
    int count = menuService.update(id, adminMenu);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("根据ID获取菜单详情")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<AdminMenu> getItem(@PathVariable Long id) {
    AdminMenu adminMenu = menuService.getItem(id);
    return CommonResult.success(adminMenu);
  }

  @ApiOperation("根据ID删除后台菜单")
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> delete(@PathVariable Long id) {
    int count = menuService.delete(id);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("分页查询后台菜单")
  @RequestMapping(value = "/list/{parentId}", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<CommonPage<AdminMenu>> list(
      @PathVariable Long parentId,
      @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<AdminMenu> menuList = menuService.list(parentId, pageSize, pageNum);
    return CommonResult.success(CommonPage.toPage(menuList));
  }

  @ApiOperation("树形结构返回所有菜单列表")
  @RequestMapping(value = "/treeList", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<List<AdminMenuNode>> treeList() {
    List<AdminMenuNode> list = menuService.treeList();
    return CommonResult.success(list);
  }

  @ApiOperation("修改菜单显示状态")
  @RequestMapping(value = "/updateHidden/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> updateHidden(
      @PathVariable Long id, @RequestParam("hidden") Integer hidden) {
    int count = menuService.updateHidden(id, hidden);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }
}
