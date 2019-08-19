package senntyou.sbs.demo.controller;

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
import senntyou.sbs.demo.dto.ArticleParam;
import senntyou.sbs.demo.dto.ArticleQueryParam;
import senntyou.sbs.demo.service.ArticleService;
import senntyou.sbs.demo.service.UserService;
import senntyou.sbs.mbg.model.Article;
import senntyou.sbs.mbg.model.User;

@RestController
@Api(tags = "ArticleController", description = "Article management")
@RequestMapping("/api/article")
public class ArticleController {
  @Autowired private ArticleService articleService;
  @Autowired private UserService userService;

  @ApiOperation("Create article")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult create(@RequestBody ArticleParam articleParam) {
    User user = userService.getCurrentUser();
    if (user == null) {
      return CommonResult.unauthorized("Not Logged-in");
    }

    Article article = articleParam.toArticle();
    article.setCreateUserId(user.getId());

    int count = articleService.create(article);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("Update article")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult update(@PathVariable long id, @RequestBody ArticleParam articleParam) {
    User user = userService.getCurrentUser();
    if (user == null) {
      return CommonResult.unauthorized("Not logged in");
    }

    Article article = articleService.getById(id);
    if (!article.getCreateUserId().equals(user.getId())) {
      return CommonResult.forbidden("No privileges");
    }

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
}
