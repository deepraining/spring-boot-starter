package dr.sbs.search.controller;

import dr.sbs.common.CommonPage;
import dr.sbs.common.CommonResult;
import dr.sbs.search.dto.ArticleDoc;
import dr.sbs.search.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {
  @Autowired private ArticleService articleService;

  @ApiOperation("导入所有文章")
  @RequestMapping(value = "/importAll", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> importAll() {
    int count = articleService.importAll();
    return CommonResult.success(count);
  }

  @ApiOperation("导入文章")
  @RequestMapping(value = "/create/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> create(@PathVariable Long id) {
    int count = articleService.create(id);
    return CommonResult.success(count);
  }

  @ApiOperation("删除")
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> delete(@PathVariable Long id) {
    int count = articleService.delete(id);
    return CommonResult.success(count);
  }

  @ApiOperation("批量删除")
  @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> delete(@RequestParam List<Long> ids) {
    int count = articleService.batchDelete(ids);
    return CommonResult.success(count);
  }

  @ApiOperation("搜索")
  @RequestMapping(value = "/search", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<CommonPage<ArticleDoc>> search(
      @RequestParam(value = "pageSize", defaultValue = "10")
          @ApiParam(value = "每页条数", defaultValue = "10")
          Integer pageSize,
      @RequestParam(value = "pageNum", defaultValue = "1")
          @ApiParam(value = "页码", defaultValue = "1")
          Integer pageNum,
      @RequestParam(value = "searchKey", defaultValue = "")
          @ApiParam(value = "搜索关键字", defaultValue = "")
          String searchKey) {
    Page<ArticleDoc> queryList = articleService.search(searchKey, pageSize, pageNum);
    return CommonResult.success(CommonPage.toPage(queryList));
  }
}
