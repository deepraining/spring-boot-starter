package senntyou.sbs.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import senntyou.sbs.admin.dto.ProductParam;
import senntyou.sbs.admin.dto.ProductQueryParam;
import senntyou.sbs.admin.dto.ProductResult;
import senntyou.sbs.admin.service.ProductService;
import senntyou.sbs.common.CommonPage;
import senntyou.sbs.common.CommonResult;
import senntyou.sbs.mbg.model.Product;

/** 商品管理Controller Created by macro on 2018/4/26. */
@Controller
@Api(tags = "ProductController", description = "商品管理")
@RequestMapping("/product")
public class ProductController {
  @Autowired private ProductService productService;

  @ApiOperation("创建商品")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult create(@RequestBody ProductParam productParam, BindingResult bindingResult) {
    int count = productService.create(productParam);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("根据商品id获取商品编辑信息")
  @RequestMapping(value = "/updateInfo/{id}", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<ProductResult> getUpdateInfo(@PathVariable Long id) {
    ProductResult productResult = productService.getUpdateInfo(id);
    return CommonResult.success(productResult);
  }

  @ApiOperation("更新商品")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult update(
      @PathVariable Long id, @RequestBody ProductParam productParam, BindingResult bindingResult) {
    int count = productService.update(id, productParam);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("查询商品")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<CommonPage<Product>> getList(
      ProductQueryParam productQueryParam,
      @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<Product> productList = productService.list(productQueryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.toPage(productList));
  }

  @ApiOperation("根据商品名称或货号模糊查询")
  @RequestMapping(value = "/simpleList", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<List<Product>> getList(String keyword) {
    List<Product> productList = productService.list(keyword);
    return CommonResult.success(productList);
  }

  @ApiOperation("批量修改审核状态")
  @RequestMapping(value = "/update/verifyStatus", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult updateVerifyStatus(
      @RequestParam("ids") List<Long> ids,
      @RequestParam("verifyStatus") Integer verifyStatus,
      @RequestParam("detail") String detail) {
    int count = productService.updateVerifyStatus(ids, verifyStatus, detail);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("批量上下架")
  @RequestMapping(value = "/update/publishStatus", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult updatePublishStatus(
      @RequestParam("ids") List<Long> ids, @RequestParam("publishStatus") Integer publishStatus) {
    int count = productService.updatePublishStatus(ids, publishStatus);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("批量推荐商品")
  @RequestMapping(value = "/update/recommendStatus", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult updateRecommendStatus(
      @RequestParam("ids") List<Long> ids,
      @RequestParam("recommendStatus") Integer recommendStatus) {
    int count = productService.updateRecommendStatus(ids, recommendStatus);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("批量设为新品")
  @RequestMapping(value = "/update/newStatus", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult updateNewStatus(
      @RequestParam("ids") List<Long> ids, @RequestParam("newStatus") Integer newStatus) {
    int count = productService.updateNewStatus(ids, newStatus);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("批量修改删除状态")
  @RequestMapping(value = "/update/deleteStatus", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult updateDeleteStatus(
      @RequestParam("ids") List<Long> ids, @RequestParam("deleteStatus") Integer deleteStatus) {
    int count = productService.updateDeleteStatus(ids, deleteStatus);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }
}
