package dr.sbs.mdb.controller;

import dr.sbs.common.CommonResult;
import dr.sbs.mdb.service.ArticleService;
import dr.sbs.mdbmbg.model.MdbArticle;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {
  @Autowired private ArticleService articleService;

  @ApiOperation("所有文章列表")
  @RequestMapping(value = "/listAll", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<List<MdbArticle>> listAll() {
    return CommonResult.success(articleService.listAll());
  }

  @ApiOperation("添加文章")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> create(@RequestBody MdbArticle article) {
    int count = articleService.create(article);
    return CommonResult.success(count);
  }

  @ApiOperation("更新文章")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> update(@PathVariable Long id, @RequestBody MdbArticle article) {
    int count = articleService.update(id, article);
    return CommonResult.success(count);
  }

  @ApiOperation("删除")
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> delete(@PathVariable Long id) {
    int count = articleService.delete(id);
    return CommonResult.success(count);
  }
}
