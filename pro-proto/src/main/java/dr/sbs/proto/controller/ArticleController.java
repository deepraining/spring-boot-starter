package dr.sbs.proto.controller;

import dr.sbs.common.CommonPage;
import dr.sbs.common.CommonResult;
import dr.sbs.mbg.model.Article;
import dr.sbs.mbg.model.FrontUser;
import dr.sbs.proto.all.AllProto;
import dr.sbs.proto.dto.ArticleCreateParam;
import dr.sbs.proto.service.ArticleService;
import dr.sbs.proto.service.UserService;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/article")
public class ArticleController {
  @Autowired private ArticleService articleService;
  @Autowired private UserService userService;

  public CommonResult<Integer> create(ArticleCreateParam articleCreateParam) {
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

  @PostMapping(
      value = "/create",
      headers = {"content-type=application/octet-stream"})
  @ResponseBody
  public ResponseEntity<byte[]> createPB(InputStream reqStream) throws IOException {
    AllProto.ArticleCreateParamProto articleCreateParamProto =
        AllProto.ArticleCreateParamProto.parseFrom(reqStream);
    ArticleCreateParam articleCreateParam = new ArticleCreateParam();
    articleCreateParam.setTitle(articleCreateParamProto.getTitle());
    articleCreateParam.setIntro(articleCreateParamProto.getIntro());
    articleCreateParam.setContent(articleCreateParamProto.getContent());

    CommonResult<Integer> result = create(articleCreateParam);

    return ProtoUtil.responseInteger(result);
  }

  public CommonResult<Integer> update(Long id, ArticleCreateParam articleCreateParam) {
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

  @PostMapping(
      value = "/update/{id}",
      headers = {"content-type=application/octet-stream"})
  @ResponseBody
  public ResponseEntity<byte[]> updatePB(@PathVariable Long id, InputStream reqStream)
      throws IOException {
    AllProto.ArticleCreateParamProto articleCreateParamProto =
        AllProto.ArticleCreateParamProto.parseFrom(reqStream);
    ArticleCreateParam articleCreateParam = new ArticleCreateParam();
    articleCreateParam.setTitle(articleCreateParamProto.getTitle());
    articleCreateParam.setIntro(articleCreateParamProto.getIntro());
    articleCreateParam.setContent(articleCreateParamProto.getContent());

    CommonResult<Integer> result = update(id, articleCreateParam);

    return ProtoUtil.responseInteger(result);
  }

  public CommonResult<Integer> delete(Long id) {
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

  @PostMapping(
      value = "/delete",
      headers = {"content-type=application/octet-stream"})
  @ResponseBody
  public ResponseEntity<byte[]> deletePB(InputStream reqStream) throws IOException {
    AllProto.LongIdParamProto longIdParamProto = AllProto.LongIdParamProto.parseFrom(reqStream);

    CommonResult<Integer> result = delete(longIdParamProto.getId());

    return ProtoUtil.responseInteger(result);
  }

  public CommonResult<CommonPage<Article>> list(
      Integer pageSize, Integer pageNum, String searchKey) {
    List<Article> queryList = articleService.list(searchKey, pageSize, pageNum);
    return CommonResult.success(CommonPage.toPage(queryList));
  }

  @PostMapping(
      value = "/list",
      headers = {"content-type=application/octet-stream"})
  @ResponseBody
  public ResponseEntity<byte[]> listPB(InputStream reqStream) throws IOException {
    AllProto.ListQueryParamProto listQueryParamProto =
        AllProto.ListQueryParamProto.parseFrom(reqStream);

    CommonResult<CommonPage<Article>> result =
        list(
            listQueryParamProto.getPageSize(),
            listQueryParamProto.getPageNum(),
            listQueryParamProto.getSearchKey());

    return ProtoUtil.responseArticlePage(result);
  }

  public CommonResult<Article> record(long id) {
    Article article = articleService.getItem(id);
    return CommonResult.success(article);
  }

  @PostMapping(
      value = "/record/{id}",
      headers = {"content-type=application/octet-stream"})
  @ResponseBody
  public ResponseEntity<byte[]> recordPB(@PathVariable long id) throws IOException {
    CommonResult<Article> result = record(id);

    return ProtoUtil.responseArticle(result);
  }
}
