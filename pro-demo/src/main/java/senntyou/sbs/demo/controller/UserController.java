package senntyou.sbs.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import senntyou.sbs.common.CommonPage;
import senntyou.sbs.common.CommonResult;
import senntyou.sbs.demo.dto.ArticleQueryParam;
import senntyou.sbs.demo.service.ArticleService;
import senntyou.sbs.demo.service.UserService;
import senntyou.sbs.mbg.model.Article;
import senntyou.sbs.mbg.model.User;

@RestController
@Api(tags = "UserController", description = "User management")
@RequestMapping("/api/user")
public class UserController {
  @Autowired private UserService userService;
  @Autowired private ArticleService articleService;

  @ApiOperation("article list")
  @RequestMapping(value = "/articles", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<CommonPage<Article>> articles(
      ArticleQueryParam articleQueryParam,
      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    User user = userService.getCurrentUser();
    articleQueryParam.setCreateUserId(user.getId());
    List<Article> queryList = articleService.list(articleQueryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.toPage(queryList));
  }
}
