package dr.sbs.admin.controller;

import com.github.pagehelper.PageInfo;
import dr.sbs.admin.dto.ArticleCreateParam;
import dr.sbs.admin.dto.ArticleRecord;
import dr.sbs.admin.service.ArticleService;
import dr.sbs.common.CommonPage;
import dr.sbs.common.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

/** 文章管理 */
@Controller
@Api(tags = "ArticleController", description = "文章管理")
@RequestMapping("/article")
public class ArticleController {
  @Autowired private ArticleService articleService;

  @ApiOperation("查询文章列表")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<CommonPage<ArticleRecord>> list(
      @RequestParam(value = "pageSize", defaultValue = "10")
          @ApiParam(value = "每页条数", defaultValue = "10")
          Integer pageSize,
      @RequestParam(value = "pageNum", defaultValue = "1")
          @ApiParam(value = "页码", defaultValue = "1")
          Integer pageNum,
      @RequestParam(value = "searchKey", defaultValue = "")
          @ApiParam(value = "搜索关键字", defaultValue = "")
          String searchKey) {
    PageInfo<ArticleRecord> articleList = articleService.list(searchKey, pageSize, pageNum);
    return CommonResult.success(CommonPage.toPage(articleList));
  }

  @ApiOperation("添加文章")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> create(
      @RequestBody @Validated ArticleCreateParam articleCreateParam, BindingResult bindingResult) {
    int count = articleService.create(articleCreateParam);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("修改文章")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> update(
      @PathVariable Long id,
      @RequestBody @Validated ArticleCreateParam articleCreateParam,
      BindingResult bindingResult) {
    int count = articleService.update(id, articleCreateParam);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("根据ID删除文章")
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> delete(@PathVariable Long id) {
    int count = articleService.delete(id);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }
}
