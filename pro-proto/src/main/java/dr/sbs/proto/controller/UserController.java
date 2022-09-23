package dr.sbs.proto.controller;

import dr.sbs.common.CommonPage;
import dr.sbs.common.CommonResult;
import dr.sbs.mbg.model.Article;
import dr.sbs.proto.all.AllProto;
import dr.sbs.proto.service.ArticleService;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/user")
public class UserController {
  @Autowired private ArticleService articleService;

  public CommonResult<CommonPage<Article>> articles(Integer pageSize, Integer pageNum) {
    List<Article> list = articleService.myList(pageSize, pageNum);
    return CommonResult.success(CommonPage.toPage(list));
  }

  @PostMapping(
      value = "/articles",
      headers = {"content-type=application/octet-stream"})
  @ResponseBody
  public ResponseEntity<byte[]> articlesPB(InputStream reqStream) throws IOException {
    AllProto.ListQueryParamProto listQueryParamProto =
        AllProto.ListQueryParamProto.parseFrom(reqStream);

    CommonResult<CommonPage<Article>> result =
        articles(listQueryParamProto.getPageSize(), listQueryParamProto.getPageNum());

    return ProtoUtil.responseArticlePage(result);
  }
}
