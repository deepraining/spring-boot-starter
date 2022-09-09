package dr.sbs.admin.controller;

import dr.sbs.admin.service.UploadService;
import dr.sbs.common.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "Upload", description = "上传管理")
@Slf4j
@Controller
@RequestMapping("/")
public class UploadController {
  @Autowired UploadService uploadService;

  @ApiOperation("文件上传")
  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<String> upload(
      @RequestParam(value = "file", required = true) MultipartFile file) {
    String url = uploadService.uploadFile(file);
    if (url != null) return CommonResult.success(url);
    return CommonResult.failed();
  }
}
