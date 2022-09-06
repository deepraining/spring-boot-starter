package dr.sbs.front.controller;

import dr.sbs.common.CommonPage;
import dr.sbs.common.CommonResult;
import dr.sbs.front.dto.ArticleCreateParam;
import dr.sbs.front.service.ArticleService;
import dr.sbs.front.service.UserService;
import dr.sbs.mbg.model.Article;
import dr.sbs.mbg.model.FrontUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "ArticleController", description = "Article management")
@RequestMapping("/api/article")
public class ArticleController {
  @Autowired private ArticleService articleService;
  @Autowired private UserService userService;

  @ApiOperation("Create article")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> create(
      @RequestBody @Validated ArticleCreateParam articleCreateParam, BindingResult bindingResult) {
    FrontUser user = userService.getCurrentUser();
    if (user == null) {
      return CommonResult.unauthorized("Not Logged-in");
    }

    int count = articleService.create(articleCreateParam);
    if (count > 0) {
      return CommonResult.success(count);
    }
    return CommonResult.failed();
  }

  @ApiOperation("Update article")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> update(
      @PathVariable Long id,
      @RequestBody @Validated ArticleCreateParam articleCreateParam,
      BindingResult bindingResult) {
    FrontUser user = userService.getCurrentUser();
    if (user == null) {
      return CommonResult.unauthorized("Not logged in");
    }

    Article article = articleService.getItem(id);
    if (!article.getFrontUserId().equals(user.getId())) {
      return CommonResult.forbidden("No privileges");
    }

    int count = articleService.update(id, articleCreateParam);
    if (count > 0) {
      return CommonResult.success(count);
    }
    return CommonResult.failed();
  }

  @ApiOperation("Delete article")
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> delete(@RequestParam Long id) {
    FrontUser user = userService.getCurrentUser();
    if (user == null) {
      return CommonResult.unauthorized("Not logged in");
    }

    Article article = articleService.getItem(id);
    if (!article.getFrontUserId().equals(user.getId())) {
      return CommonResult.forbidden("No privileges");
    }

    int count = articleService.delete(id);
    if (count > 0) {
      return CommonResult.success(count);
    }
    return CommonResult.failed();
  }

  @ApiOperation("Query list")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<CommonPage<Article>> list(
      @RequestParam(value = "pageSize", defaultValue = "10")
          @ApiParam(value = "每页条数", defaultValue = "10")
          Integer pageSize,
      @RequestParam(value = "pageNum", defaultValue = "1")
          @ApiParam(value = "页码", defaultValue = "1")
          Integer pageNum,
      @RequestParam(value = "searchKey", defaultValue = "")
          @ApiParam(value = "搜索关键字", defaultValue = "")
          String searchKey) {
    List<Article> queryList = articleService.list(searchKey, pageSize, pageNum);
    return CommonResult.success(CommonPage.toPage(queryList));
  }

  @ApiOperation("Get a record")
  @RequestMapping(value = "/record/{id}", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<Article> record(@PathVariable long id) {
    Article article = articleService.getItem(id);
    return CommonResult.success(article);
  }
}
