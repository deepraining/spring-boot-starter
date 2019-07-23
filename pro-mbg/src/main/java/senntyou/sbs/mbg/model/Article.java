package senntyou.sbs.mbg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {
    @JsonIgnore
    private Integer id;

    /**
     * uuid
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "uuid")
    private String uuid;

    /**
     * title
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "title")
    private String title;

    /**
     * read count
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "read count")
    private Integer readCount;

    /**
     * support count
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "support count")
    private Integer supportCount;

    /**
     * article summary
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "article summary")
    private String intro;

    /**
     * creator user_uuid
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "creator user_uuid")
    private String createUserUuid;

    /**
     * deleted or not
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "deleted or not")
    private Boolean deleted;

    /**
     * create time
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "create time")
    private Date createTime;

    /**
     * update time
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "update time")
    private Date updateTime;

    /**
     * article content
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "article content")
    private String content;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getCreateUserUuid() {
        return createUserUuid;
    }

    public void setCreateUserUuid(String createUserUuid) {
        this.createUserUuid = createUserUuid;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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
        sb.append(", uuid=").append(uuid);
        sb.append(", title=").append(title);
        sb.append(", readCount=").append(readCount);
        sb.append(", supportCount=").append(supportCount);
        sb.append(", intro=").append(intro);
        sb.append(", createUserUuid=").append(createUserUuid);
        sb.append(", deleted=").append(deleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", content=").append(content);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
