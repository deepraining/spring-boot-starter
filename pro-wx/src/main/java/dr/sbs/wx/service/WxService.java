package dr.sbs.wx.service;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;

public interface WxService {
  // 小程序支付示例
  WxPayMpOrderResult makeMiniPay();

  // 服务号支付示例
  WxPayMpOrderResult makeMpPay();

  // 微信支付回调处理
  int wxPayNotify(WxPayOrderNotifyResult notifyResult);
}
