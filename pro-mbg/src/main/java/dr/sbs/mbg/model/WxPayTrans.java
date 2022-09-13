package dr.sbs.mbg.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WxPayTrans implements Serializable {
    /**
     * 支付流水号（微信支付回调的 transaction_id 字段）
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "支付流水号（微信支付回调的 transaction_id 字段）", position = 1)
    private String transId;

    /**
     * 业务单据号（微信支付回调的 out_trade_no 字段）
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "业务单据号（微信支付回调的 out_trade_no 字段）", position = 2)
    private String billNo;

    /**
     * 支付用户openId（微信支付回调的 openid 字段）
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "支付用户openId（微信支付回调的 openid 字段）", position = 3)
    private String openId;

    /**
     * 收款商户号（微信支付回调的 mch_id 字段）
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "收款商户号（微信支付回调的 mch_id 字段）", position = 4)
    private String mchId;

    /**
     * 应用的appId（微信支付回调的 appid 字段）
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "应用的appId（微信支付回调的 appid 字段）", position = 5)
    private String appId;

    /**
     * 订单金额（微信支付回调的 total_fee/100 字段）
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "订单金额（微信支付回调的 total_fee/100 字段）", position = 6)
    private BigDecimal totalFee;

    /**
     * 支付金额（微信支付回调的 cash_fee/100 字段）
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "支付金额（微信支付回调的 cash_fee/100 字段）", position = 7)
    private BigDecimal cashFee;

    /**
     * 优惠券支付金额（微信支付回调的 coupon_fee/100 字段）
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "优惠券支付金额（微信支付回调的 coupon_fee/100 字段）", position = 8)
    private BigDecimal couponFee;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "创建时间", position = 9)
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "更新时间", position = 10)
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public BigDecimal getCashFee() {
        return cashFee;
    }

    public void setCashFee(BigDecimal cashFee) {
        this.cashFee = cashFee;
    }

    public BigDecimal getCouponFee() {
        return couponFee;
    }

    public void setCouponFee(BigDecimal couponFee) {
        this.couponFee = couponFee;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", transId=").append(transId);
        sb.append(", billNo=").append(billNo);
        sb.append(", openId=").append(openId);
        sb.append(", mchId=").append(mchId);
        sb.append(", appId=").append(appId);
        sb.append(", totalFee=").append(totalFee);
        sb.append(", cashFee=").append(cashFee);
        sb.append(", couponFee=").append(couponFee);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}