package senntyou.sbs.jwtdemo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import senntyou.sbs.common.CommonPage;
import senntyou.sbs.common.CommonResult;
import senntyou.sbs.jwtdemo.dto.ArticleParam;
import senntyou.sbs.jwtdemo.dto.ArticleQueryParam;
import senntyou.sbs.jwtdemo.service.ArticleService;
import senntyou.sbs.mbg.model.Article;

@RestController
@Api(tags = "ArticleController", description = "Article management")
@RequestMapping("/article")
public class ArticleController {
  @Autowired private ArticleService articleService;

  @ApiOperation("Update article")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult update(@PathVariable long id, @RequestBody ArticleParam articleParam) {

    Article newArticle = articleParam.toArticle();

    int count = articleService.update(id, newArticle);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("Query list")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<CommonPage<Article>> list(
      ArticleQueryParam articleQueryParam,
      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<Article> queryList = articleService.list(articleQueryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.toPage(queryList));
  }

  @ApiOperation("Get a record")
  @RequestMapping(value = "/record/{id}", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<Article> record(@PathVariable long id) {
    Article article = articleService.getRecord(id);
    return CommonResult.success(article);
  }

  @ApiOperation("Delete article")
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult delete(@PathVariable long id) {

    Article article = new Article();
    article.setDeleted(1);

    int count = articleService.update(id, article);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }
}
