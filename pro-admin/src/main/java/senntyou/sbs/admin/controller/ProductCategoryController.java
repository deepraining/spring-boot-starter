package senntyou.sbs.admin.controller;

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
import senntyou.sbs.admin.dto.ProductCategoryParam;
import senntyou.sbs.admin.dto.ProductCategoryWithChildrenItem;
import senntyou.sbs.admin.service.ProductCategoryService;
import senntyou.sbs.common.CommonPage;
import senntyou.sbs.common.CommonResult;
import senntyou.sbs.mbg.model.ProductCategory;

/** 商品分类模块Controller Created by macro on 2018/4/26. */
@Controller
@Api(tags = "ProductCategoryController", description = "商品分类管理")
@RequestMapping("/productCategory")
public class ProductCategoryController {
  @Autowired private ProductCategoryService productCategoryService;

  @ApiOperation("添加产品分类")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult create(
      @Validated @RequestBody ProductCategoryParam productCategoryParam, BindingResult result) {
    int count = productCategoryService.create(productCategoryParam);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("修改商品分类")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult update(
      @PathVariable Long id,
      @Validated @RequestBody ProductCategoryParam productCategoryParam,
      BindingResult result) {
    int count = productCategoryService.update(id, productCategoryParam);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("分页查询商品分类")
  @RequestMapping(value = "/list/{parentId}", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<CommonPage<ProductCategory>> getList(
      @PathVariable Long parentId,
      @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<ProductCategory> productCategoryList =
        productCategoryService.getList(parentId, pageSize, pageNum);
    return CommonResult.success(CommonPage.toPage(productCategoryList));
  }

  @ApiOperation("根据id获取商品分类")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<ProductCategory> getItem(@PathVariable Long id) {
    ProductCategory productCategory = productCategoryService.getItem(id);
    return CommonResult.success(productCategory);
  }

  @ApiOperation("删除商品分类")
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult delete(@PathVariable Long id) {
    int count = productCategoryService.delete(id);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("修改导航栏显示状态")
  @RequestMapping(value = "/update/navStatus", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult updateNavStatus(
      @RequestParam("ids") List<Long> ids, @RequestParam("navStatus") Integer navStatus) {
    int count = productCategoryService.updateNavStatus(ids, navStatus);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("修改显示状态")
  @RequestMapping(value = "/update/showStatus", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult updateShowStatus(
      @RequestParam("ids") List<Long> ids, @RequestParam("showStatus") Integer showStatus) {
    int count = productCategoryService.updateShowStatus(ids, showStatus);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("查询所有一级分类及子分类")
  @RequestMapping(value = "/list/withChildren", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<List<ProductCategoryWithChildrenItem>> listWithChildren() {
    List<ProductCategoryWithChildrenItem> list = productCategoryService.listWithChildren();
    return CommonResult.success(list);
  }
}
