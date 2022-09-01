package dr.sbs.admin.controller;

import dr.sbs.admin.dto.ProductAttributeCategoryItem;
import dr.sbs.admin.service.ProductAttributeCategoryService;
import dr.sbs.common.CommonPage;
import dr.sbs.common.CommonResult;
import dr.sbs.mbg.model.ProductAttributeCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/** 商品属性分类Controller */
@Controller
@Api(tags = "ProductAttributeCategoryController", description = "商品属性分类管理")
@RequestMapping("/productAttribute/category")
public class ProductAttributeCategoryController {
  @Autowired private ProductAttributeCategoryService productAttributeCategoryService;

  @ApiOperation("添加商品属性分类")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult create(@RequestParam String name) {
    int count = productAttributeCategoryService.create(name);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("修改商品属性分类")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult update(@PathVariable Long id, @RequestParam String name) {
    int count = productAttributeCategoryService.update(id, name);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("删除单个商品属性分类")
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult delete(@PathVariable Long id) {
    int count = productAttributeCategoryService.delete(id);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("获取单个商品属性分类信息")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<ProductAttributeCategory> getItem(@PathVariable Long id) {
    ProductAttributeCategory productAttributeCategory = productAttributeCategoryService.getItem(id);
    return CommonResult.success(productAttributeCategory);
  }

  @ApiOperation("分页获取所有商品属性分类")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<CommonPage<ProductAttributeCategory>> getList(
      @RequestParam(defaultValue = "5") Integer pageSize,
      @RequestParam(defaultValue = "1") Integer pageNum) {
    List<ProductAttributeCategory> productAttributeCategoryList =
        productAttributeCategoryService.getList(pageSize, pageNum);
    return CommonResult.success(CommonPage.toPage(productAttributeCategoryList));
  }

  @ApiOperation("获取所有商品属性分类及其下属性")
  @RequestMapping(value = "/list/withAttr", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<List<ProductAttributeCategoryItem>> getListWithAttr() {
    List<ProductAttributeCategoryItem> productAttributeCategoryResultList =
        productAttributeCategoryService.getListWithAttr();
    return CommonResult.success(productAttributeCategoryResultList);
  }
}
