package dr.sbs.wx.service;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import dr.sbs.mbg.mapper.WxPayTransMapper;
import dr.sbs.mbg.model.WxPayTrans;
import dr.sbs.wx.config.WxPayConfiguration;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WxServiceImpl implements WxService {
  @Autowired private WxPayTransMapper wxPayTransMapper;

  @Override
  public WxPayMpOrderResult makeMiniPay() {
    WxPayUnifiedOrderRequest orderRequest = WxPayUnifiedOrderRequest.newBuilder().build();
    // 支付消息的标题
    orderRequest.setBody("");
    // 我方的订单Id
    orderRequest.setOutTradeNo("");
    // 支付金额，单位分
    orderRequest.setTotalFee(1);
    // 我方服务器IP
    orderRequest.setSpbillCreateIp("");

    try {
      // 用户openId
      orderRequest.setOpenid("");
      WxPayMpOrderResult orderResult =
          WxPayConfiguration.getMiniWxPayService().createOrder(orderRequest);
      // orderResult 用于前端发起微信客户端支付
      return orderResult;
    } catch (WxPayException e) {
      log.error("发起支付发生错误:{}", e.getMessage());
      return null;
    }
  }

  @Override
  public WxPayMpOrderResult makeMpPay() {
    WxPayUnifiedOrderRequest orderRequest = WxPayUnifiedOrderRequest.newBuilder().build();
    // 支付消息的标题
    orderRequest.setBody("");
    // 我方的订单Id
    orderRequest.setOutTradeNo("");
    // 支付金额，单位分
    orderRequest.setTotalFee(1);
    // 我方服务器IP
    orderRequest.setSpbillCreateIp("");

    try {
      // 用户openId
      orderRequest.setOpenid("");
      WxPayMpOrderResult orderResult =
          WxPayConfiguration.getMpWxPayService().createOrder(orderRequest);
      // orderResult 用于前端发起微信客户端支付
      return orderResult;
    } catch (WxPayException e) {
      log.error("发起支付发生错误:{}", e.getMessage());
      return null;
    }
  }

  @Override
  public int wxPayNotify(WxPayOrderNotifyResult notifyResult) {
    // 微信支付流水号
    String transId = notifyResult.getTransactionId();
    // 我方订单号
    String outTradeNo = notifyResult.getOutTradeNo();

    Long orderId;
    try {
      orderId = Long.parseLong(outTradeNo);
    } catch (Exception e) {
      log.error("微信支付回调，转换outTradeNo为orderId发生错误，outTradeNo: {}！", outTradeNo);
      return -1;
    }

    WxPayTrans wxPayTrans = new WxPayTrans();
    wxPayTrans.setTransId(notifyResult.getTransactionId());
    wxPayTrans.setBillNo(outTradeNo);
    wxPayTrans.setOpenId(notifyResult.getOpenid());
    wxPayTrans.setMchId(notifyResult.getMchId());
    wxPayTrans.setAppId(notifyResult.getAppid());
    wxPayTrans.setTotalFee(
        BigDecimal.valueOf((float) notifyResult.getTotalFee() / 100)
            .setScale(2, RoundingMode.HALF_UP));
    wxPayTrans.setCashFee(
        BigDecimal.valueOf((float) notifyResult.getCashFee() / 100)
            .setScale(2, RoundingMode.HALF_UP));
    // couponFee可能是null
    if (notifyResult.getCouponFee() != null) {
      wxPayTrans.setCouponFee(
          BigDecimal.valueOf((float) notifyResult.getCouponFee() / 100)
              .setScale(2, RoundingMode.HALF_UP));
    } else {
      wxPayTrans.setCouponFee(BigDecimal.ZERO);
    }
    // 插入一条微信支付数据
    wxPayTransMapper.insertSelective(wxPayTrans);

    return 1;
  }
}
