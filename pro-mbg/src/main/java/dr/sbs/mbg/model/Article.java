package dr.sbs.mbg.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {
    private Long id;

    /**
     * title
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "title", position = 2)
    private String title;

    /**
     * read count
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "read count", position = 3)
    private Integer readCount;

    /**
     * support count
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "support count", position = 4)
    private Integer supportCount;

    /**
     * article summary
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "article summary", position = 5)
    private String intro;

    /**
     * creator front_user id
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "creator front_user id", position = 6)
    private Long frontUserId;

    /**
     * status(1: normal, 0: blocked)
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "status(1: normal, 0: blocked)", position = 7)
    private Byte status;

    /**
     * create time
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "create time", position = 8)
    private Date createTime;

    /**
     * update time
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "update time", position = 9)
    private Date updateTime;

    /**
     * article content
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "article content", position = 10)
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
        sb.append(", readCount=").append(readCount);
        sb.append(", supportCount=").append(supportCount);
        sb.append(", intro=").append(intro);
        sb.append(", frontUserId=").append(frontUserId);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", content=").append(content);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}