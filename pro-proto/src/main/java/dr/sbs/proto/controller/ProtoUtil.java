package dr.sbs.proto.controller;

import cn.hutool.core.date.DateUtil;
import dr.sbs.common.CommonPage;
import dr.sbs.common.CommonResult;
import dr.sbs.mbg.model.Article;
import dr.sbs.mbg.model.FrontUser;
import dr.sbs.proto.all.AllProto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ProtoUtil {
  // CommonResult => proto
  public static void copyCommonResultProto(
      AllProto.IntegerResultProto.Builder builder, CommonResult commonResult) {
    builder.setCode(commonResult.getCode());
    builder.setMessage(commonResult.getMessage());
  }

  public static void copyCommonResultProto(
      AllProto.ArticleResultProto.Builder builder, CommonResult commonResult) {
    builder.setCode(commonResult.getCode());
    builder.setMessage(commonResult.getMessage());
  }

  public static void copyCommonResultProto(
      AllProto.FrontUserResultProto.Builder builder, CommonResult commonResult) {
    builder.setCode(commonResult.getCode());
    builder.setMessage(commonResult.getMessage());
  }

  public static void copyCommonResultProto(
      AllProto.ArticlePageResultProto.Builder builder, CommonResult commonResult) {
    builder.setCode(commonResult.getCode());
    builder.setMessage(commonResult.getMessage());
  }

  public static void copyCommonResultProto(
      AllProto.FrontUserPageResultProto.Builder builder, CommonResult commonResult) {
    builder.setCode(commonResult.getCode());
    builder.setMessage(commonResult.getMessage());
  }

  // CommonPage => proto
  public static void copyCommonPageProto(
      AllProto.ArticlePageProto.Builder builder, CommonPage commonPage) {
    builder.setPageNum(commonPage.getPageNum());
    builder.setPageSize(commonPage.getPageSize());
    builder.setPages(commonPage.getPages());
    builder.setTotal(commonPage.getTotal());
  }

  public static void copyCommonPageProto(
      AllProto.FrontUserPageProto.Builder builder, CommonPage commonPage) {
    builder.setPageNum(commonPage.getPageNum());
    builder.setPageSize(commonPage.getPageSize());
    builder.setPages(commonPage.getPages());
    builder.setTotal(commonPage.getTotal());
  }

  // FrontUser => proto
  public static void copyFrontUserProto(
      AllProto.FrontUserProto.Builder builder, FrontUser frontUser) {
    if (frontUser == null) return;

    if (frontUser.getId() != null) builder.setId(frontUser.getId());
    if (frontUser.getUsername() != null) builder.setUsername(frontUser.getUsername());
    if (frontUser.getEmail() != null) builder.setEmail(frontUser.getEmail());
    if (frontUser.getPassword() != null) builder.setPassword(frontUser.getPassword());
    if (frontUser.getStatus() != null) builder.setStatus(frontUser.getStatus());
    if (frontUser.getCreateTime() != null)
      builder.setCreateTime(DateUtil.formatDateTime(frontUser.getCreateTime()));
    if (frontUser.getUpdateTime() != null)
      builder.setUpdateTime(DateUtil.formatDateTime(frontUser.getUpdateTime()));
  }

  // Article => proto
  public static void copyArticleProto(AllProto.ArticleProto.Builder builder, Article article) {
    if (article == null) return;

    if (article.getId() != null) builder.setId(article.getId());
    if (article.getTitle() != null) builder.setTitle(article.getTitle());
    if (article.getIntro() != null) builder.setIntro(article.getIntro());
    if (article.getFrontUserId() != null) builder.setFrontUserId(article.getFrontUserId());
    if (article.getReadCount() != null) builder.setReadCount(article.getReadCount());
    if (article.getSupportCount() != null) builder.setSupportCount(article.getSupportCount());
    if (article.getStatus() != null) builder.setStatus(article.getStatus());
    if (article.getCreateTime() != null)
      builder.setCreateTime(DateUtil.formatDateTime(article.getCreateTime()));
    if (article.getUpdateTime() != null)
      builder.setUpdateTime(DateUtil.formatDateTime(article.getUpdateTime()));
    if (article.getContent() != null) builder.setContent(article.getContent());
  }

  // response CommonResult<Integer>
  public static ResponseEntity<byte[]> responseInteger(CommonResult<Integer> result) {
    AllProto.IntegerResultProto.Builder builder = AllProto.IntegerResultProto.newBuilder();
    copyCommonResultProto(builder, result);
    builder.setData((int) result.getCode());

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    return new ResponseEntity<>(builder.build().toByteArray(), httpHeaders, HttpStatus.OK);
  }

  // response CommonResult<FrontUser>
  public static ResponseEntity<byte[]> responseFrontUser(CommonResult<FrontUser> result) {
    AllProto.FrontUserResultProto.Builder builder = AllProto.FrontUserResultProto.newBuilder();
    copyCommonResultProto(builder, result);
    AllProto.FrontUserProto.Builder dataBuilder = AllProto.FrontUserProto.newBuilder();
    copyFrontUserProto(dataBuilder, result.getData());
    builder.setData(dataBuilder.build());

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    return new ResponseEntity<>(builder.build().toByteArray(), httpHeaders, HttpStatus.OK);
  }

  // response CommonResult<Article>
  public static ResponseEntity<byte[]> responseArticle(CommonResult<Article> result) {
    AllProto.ArticleResultProto.Builder builder = AllProto.ArticleResultProto.newBuilder();
    copyCommonResultProto(builder, result);
    AllProto.ArticleProto.Builder dataBuilder = AllProto.ArticleProto.newBuilder();
    copyArticleProto(dataBuilder, result.getData());
    builder.setData(dataBuilder.build());

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    return new ResponseEntity<>(builder.build().toByteArray(), httpHeaders, HttpStatus.OK);
  }

  // response CommonResult<CommonPage<Article>>
  public static ResponseEntity<byte[]> responseArticlePage(
      CommonResult<CommonPage<Article>> result) {
    AllProto.ArticlePageResultProto.Builder builder = AllProto.ArticlePageResultProto.newBuilder();
    copyCommonResultProto(builder, result);
    AllProto.ArticlePageProto.Builder dataBuilder = AllProto.ArticlePageProto.newBuilder();
    copyCommonPageProto(dataBuilder, result.getData());

    for (Article article : result.getData().getList()) {
      AllProto.ArticleProto.Builder itemBuilder = AllProto.ArticleProto.newBuilder();
      copyArticleProto(itemBuilder, article);
      dataBuilder.addList(itemBuilder.build());
    }

    builder.setData(dataBuilder.build());

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    return new ResponseEntity<>(builder.build().toByteArray(), httpHeaders, HttpStatus.OK);
  }

  // response CommonResult<CommonPage<FrontUser>>
  public static ResponseEntity<byte[]> responseFrontUserPage(
      CommonResult<CommonPage<FrontUser>> result) {
    AllProto.FrontUserPageResultProto.Builder builder =
        AllProto.FrontUserPageResultProto.newBuilder();
    copyCommonResultProto(builder, result);
    AllProto.FrontUserPageProto.Builder dataBuilder = AllProto.FrontUserPageProto.newBuilder();
    copyCommonPageProto(dataBuilder, result.getData());

    for (FrontUser frontUser : result.getData().getList()) {
      AllProto.FrontUserProto.Builder itemBuilder = AllProto.FrontUserProto.newBuilder();
      copyFrontUserProto(itemBuilder, frontUser);
      dataBuilder.addList(itemBuilder.build());
    }

    builder.setData(dataBuilder.build());

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    return new ResponseEntity<>(builder.build().toByteArray(), httpHeaders, HttpStatus.OK);
  }
}
