package dr.sbs.wx.controller;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import dr.sbs.common.CommonResult;
import dr.sbs.wx.config.WxPayConfiguration;
import dr.sbs.wx.service.WxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/** 订单管理 */
@Controller
@Api(tags = "Wx")
@RequestMapping("/wx")
public class WxController extends BaseController {
  @Autowired private WxService wxService;

  // 微信支付回调接口
  // 不用swagger加入到api文档中
  @RequestMapping(value = "/wxPayNotify")
  @ResponseBody
  public String wxPayNotify(@RequestBody String xmlData) throws WxPayException {
    final WxPayOrderNotifyResult notifyResult =
        WxPayConfiguration.getMiniWxPayService().parseOrderNotifyResult(xmlData);

    int result = wxService.wxPayNotify(notifyResult);

    if (result > 0) {
      return WxPayNotifyResponse.success("成功");
    }
    return WxPayNotifyResponse.fail("失败");
  }

  @ApiOperation("小程序支付示例")
  @RequestMapping(value = "/makeMiniPay", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<WxPayMpOrderResult> makeMiniPay() {
    if (!getUserIsLoggedIn()) return CommonResult.unauthorized(null);

    WxPayMpOrderResult result = wxService.makeMiniPay();
    if (result != null) {
      return CommonResult.success(result);
    }
    return CommonResult.failed();
  }

  @ApiOperation("服务号支付示例")
  @RequestMapping(value = "/makeMpPay", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<WxPayMpOrderResult> makeMpPay() {
    if (!getUserIsLoggedIn()) return CommonResult.unauthorized(null);

    WxPayMpOrderResult result = wxService.makeMpPay();
    if (result != null) {
      return CommonResult.success(result);
    }
    return CommonResult.failed();
  }
}
