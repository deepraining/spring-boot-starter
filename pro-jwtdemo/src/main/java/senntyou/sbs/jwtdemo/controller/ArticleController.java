package senntyou.sbs.jwtdemo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import senntyou.sbs.common.CommonPage;
import senntyou.sbs.common.CommonResult;
import senntyou.sbs.gen.model.Article;
import senntyou.sbs.gen.model.User;
import senntyou.sbs.jwtdemo.dto.ArticleParam;
import senntyou.sbs.jwtdemo.dto.ArticleQueryParam;
import senntyou.sbs.jwtdemo.dto.ArticleResult;
import senntyou.sbs.jwtdemo.service.ArticleService;
import senntyou.sbs.jwtdemo.service.UserService;

@RestController
@Api(tags = "ArticleController", description = "Article management")
@RequestMapping("/api/article")
public class ArticleController {
  @Autowired private ArticleService articleService;
  @Autowired private UserService userService;

  @ApiOperation("Create article")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult create(@RequestBody ArticleParam articleParam, BindingResult bindingResult) {
    User user = userService.getCurrentUser();
    if (user == null) {
      return CommonResult.unauthorized("Not Logged-in");
    }

    Article article = articleParam.toArticle();
    article.setCreateUserUuid(user.getUuid());

    int count = articleService.create(article);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("Update article")
  @RequestMapping(value = "/update/{uuid}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult update(
      @PathVariable String uuid,
      @RequestBody ArticleParam articleParam,
      BindingResult bindingResult) {
    User user = userService.getCurrentUser();
    if (user == null) {
      return CommonResult.unauthorized("Not logged in");
    }

    Article article = articleService.getByUuid(uuid);
    if (!article.getCreateUserUuid().equals(user.getUuid())) {
      return CommonResult.forbidden("No privileges");
    }

    Article newArticle = articleParam.toArticle();

    int count = articleService.update(uuid, newArticle);
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
    List<Article> productList = articleService.list(articleQueryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.toPage(productList));
  }

  @ApiOperation("Get a record")
  @RequestMapping(value = "/record/{uuid}", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<ArticleResult> record(@PathVariable String uuid) {
    ArticleResult articleResult = articleService.getRecord(uuid);
    return CommonResult.success(articleResult);
  }
}
