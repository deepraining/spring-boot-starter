package dr.sbs.mdbmbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class MdbArticle implements Serializable {
  private Long id;

  /**
   * 标题
   *
   * @mbg.generated
   */
  @ApiModelProperty(value = "标题", position = 2)
  private String title;

  /**
   * 简介
   *
   * @mbg.generated
   */
  @ApiModelProperty(value = "简介", position = 3)
  private String intro;

  /**
   * 创建者 front_user id
   *
   * @mbg.generated
   */
  @ApiModelProperty(value = "创建者 front_user id", position = 4)
  private Long frontUserId;

  /**
   * 阅读数
   *
   * @mbg.generated
   */
  @ApiModelProperty(value = "阅读数", position = 5)
  private Integer readCount;

  /**
   * 点赞数
   *
   * @mbg.generated
   */
  @ApiModelProperty(value = "点赞数", position = 6)
  private Integer supportCount;

  /**
   * 状态(1: 可用, 0: 禁用)
   *
   * @mbg.generated
   */
  @ApiModelProperty(value = "状态(1: 可用, 0: 禁用)", position = 7)
  private Byte status;

  /**
   * 创建时间
   *
   * @mbg.generated
   */
  @ApiModelProperty(value = "创建时间", position = 8)
  private Date createTime;

  /**
   * 更新时间
   *
   * @mbg.generated
   */
  @ApiModelProperty(value = "更新时间", position = 9)
  private Date updateTime;

  /**
   * 内容
   *
   * @mbg.generated
   */
  @ApiModelProperty(value = "内容", position = 10)
  private String content;

  private static final long serialVersionUID = 1L;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getIntro() {
    return intro;
  }

  public void setIntro(String intro) {
    this.intro = intro;
  }

  public Long getFrontUserId() {
    return frontUserId;
  }

  public void setFrontUserId(Long frontUserId) {
    this.frontUserId = frontUserId;
  }

  public Integer getReadCount() {
    return readCount;
  }

  public void setReadCount(Integer readCount) {
    this.readCount = readCount;
  }

  public Integer getSupportCount() {
    return supportCount;
  }

  public void setSupportCount(Integer supportCount) {
    this.supportCount = supportCount;
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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getClass().getSimpleName());
    sb.append(" [");
    sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", title=").append(title);
    sb.append(", intro=").append(intro);
    sb.append(", frontUserId=").append(frontUserId);
    sb.append(", readCount=").append(readCount);
    sb.append(", supportCount=").append(supportCount);
    sb.append(", status=").append(status);
    sb.append(", createTime=").append(createTime);
    sb.append(", updateTime=").append(updateTime);
    sb.append(", content=").append(content);
    sb.append(", serialVersionUID=").append(serialVersionUID);
    sb.append("]");
    return sb.toString();
  }
}
