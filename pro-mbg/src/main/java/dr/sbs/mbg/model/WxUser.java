package dr.sbs.mbg.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class WxUser implements Serializable {
    /**
     * 主键Id(分布式生成Id)
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "主键Id(分布式生成Id)", position = 1)
    private Long id;

    /**
     * 昵称
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "昵称", position = 2)
    private String nickname;

    /**
     * 头像
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "头像", position = 3)
    private String avatar;

    /**
     * 性别（1：男，2：女，0：未知）
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "性别（1：男，2：女，0：未知）", position = 4)
    private Byte gender;

    /**
     * 生日
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "生日", position = 5)
    private Date birthday;

    /**
     * 省
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "省", position = 6)
    private String province;

    /**
     * 市
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "市", position = 7)
    private String city;

    /**
     * 区
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "区", position = 8)
    private String district;

    /**
     * 手机号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "手机号", position = 9)
    private String phone;

    /**
     * 注册日期
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "注册日期", position = 10)
    private Date registerDate;

    /**
     * 最后使用日期
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "最后使用日期", position = 11)
    private Date lastLogin;

    /**
     * 微信unionId
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "微信unionId", position = 12)
    private String unionId;

    /**
     * 小程序openId
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "小程序openId", position = 13)
    private String miniOpenId;

    /**
     * 公众号openId
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "公众号openId", position = 14)
    private String mpOpenId;

    /**
     * 用户状态（1：可用，0：禁用）
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "用户状态（1：可用，0：禁用）", position = 15)
    private Byte status;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "创建时间", position = 16)
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "更新时间", position = 17)
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getMiniOpenId() {
        return miniOpenId;
    }

    public void setMiniOpenId(String miniOpenId) {
        this.miniOpenId = miniOpenId;
    }

    public String getMpOpenId() {
        return mpOpenId;
    }

    public void setMpOpenId(String mpOpenId) {
        this.mpOpenId = mpOpenId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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
        sb.append(", id=").append(id);
        sb.append(", nickname=").append(nickname);
        sb.append(", avatar=").append(avatar);
        sb.append(", gender=").append(gender);
        sb.append(", birthday=").append(birthday);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", district=").append(district);
        sb.append(", phone=").append(phone);
        sb.append(", registerDate=").append(registerDate);
        sb.append(", lastLogin=").append(lastLogin);
        sb.append(", unionId=").append(unionId);
        sb.append(", miniOpenId=").append(miniOpenId);
        sb.append(", mpOpenId=").append(mpOpenId);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}