package dr.sbs.front.controller;

import dr.sbs.common.CommonPage;
import dr.sbs.common.CommonResult;
import dr.sbs.front.service.ArticleService;
import dr.sbs.mbg.model.Article;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
  @Autowired private ArticleService articleService;

  @ApiOperation("article list")
  @RequestMapping(value = "/articles", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<CommonPage<Article>> articles(
      @RequestParam(value = "pageSize", defaultValue = "10")
          @ApiParam(value = "每页条数", defaultValue = "10")
          Integer pageSize,
      @RequestParam(value = "pageNum", defaultValue = "1")
          @ApiParam(value = "页码", defaultValue = "1")
          Integer pageNum) {
    List<Article> list = articleService.myList(pageSize, pageNum);
    return CommonResult.success(CommonPage.toPage(list));
  }
}
