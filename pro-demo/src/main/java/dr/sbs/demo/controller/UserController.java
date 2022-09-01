package dr.sbs.demo.controller;

import dr.sbs.common.CommonPage;
import dr.sbs.common.CommonResult;
import dr.sbs.demo.dto.ArticleQueryParam;
import dr.sbs.demo.service.ArticleService;
import dr.sbs.demo.service.UserService;
import dr.sbs.mbg.model.Article;
import dr.sbs.mbg.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
