package dr.sbs.wx.controller;

import cn.hutool.core.util.URLUtil;
import dr.sbs.common.CommonResult;
import dr.sbs.wx.Constants;
import dr.sbs.wx.config.WxMpConfiguration;
import dr.sbs.wx.service.MiscService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/** 杂项管理 */
@Slf4j
@Controller
@Api(tags = "Misc", description = "杂项管理")
@RequestMapping("/misc")
public class MiscController {
  @Autowired private MiscService miscService;

  @ApiOperation(value = "获取微信手机号")
  @RequestMapping(value = "/getWxPhone", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<String> getWxPhone(
      @RequestParam(value = "code", required = true) @ApiParam(value = "微信授权code", required = true)
          String code,
      @RequestParam(value = "encryptedData", required = true)
          @ApiParam(value = "微信授权encryptedData", required = true)
          String encryptedData,
      @RequestParam(value = "iv", required = true) @ApiParam(value = "微信授权iv", required = true)
          String iv) {
    String phone = miscService.getWxPhone(code, encryptedData, iv);
    if (!StringUtils.isEmpty(phone)) {
      return CommonResult.success(phone);
    }
    return CommonResult.failed();
  }

  @ApiOperation(value = "创建调用jsapi时所需要的签名")
  @RequestMapping(value = "/createJsApiSign", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<WxJsapiSignature> createJsApiSign(
      @RequestParam(value = "url", required = true) @ApiParam(value = "页面url", required = true)
          String url) {
    if (Constants.isLocalDevEnv) return CommonResult.success(new WxJsapiSignature());

    try {
      WxJsapiSignature signature = WxMpConfiguration.getWxMpService().createJsapiSignature(url);
      return CommonResult.success(signature);
    } catch (WxErrorException e) {
      log.error("创建调用jsapi时所需要的签名发生错误: {}！", e.getMessage());
      return CommonResult.failed("签名失败");
    }
  }

  @ApiOperation(value = "微信授权跳转（因为h5授权域名只能填两个，测试环境与产品环境之外的授权通过此跳转）")
  @RequestMapping(value = "/wxAuthRedirect", method = RequestMethod.GET)
  @ResponseBody
  public ModelAndView wxAuthRedirect(
      @RequestParam(value = "code", required = true) @ApiParam(value = "微信授权code", required = true)
          String code,
      @RequestParam(value = "state", required = true)
          @ApiParam(value = "微信授权state", required = true)
          String state,
      @RequestParam(value = "redirectUrl", required = true)
          @ApiParam(value = "跳转url", required = true)
          String redirectUrl) {
    String urlParams = "code=" + code + "&state=" + state;
    String url = URLUtil.decode(redirectUrl);
    url += url.contains("?") ? '&' : '?' + urlParams;
    return new ModelAndView(new RedirectView(url));
  }
}
