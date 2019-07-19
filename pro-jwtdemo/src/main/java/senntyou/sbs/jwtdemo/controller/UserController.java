package senntyou.sbs.jwtdemo.controller;

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
import senntyou.sbs.gen.model.Article;
import senntyou.sbs.gen.model.User;
import senntyou.sbs.jwtdemo.dto.ArticleQueryParam;
import senntyou.sbs.jwtdemo.service.ArticleService;
import senntyou.sbs.jwtdemo.service.UserService;

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
    articleQueryParam.setCreateUserUuid(user.getUuid());
    List<Article> productList = articleService.list(articleQueryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.toPage(productList));
  }
}
