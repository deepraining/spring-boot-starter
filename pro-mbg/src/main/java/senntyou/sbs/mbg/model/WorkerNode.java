package senntyou.sbs.mbg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class WorkerNode implements Serializable {
    /**
     * auto increment id
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "auto increment id")
    private Long id;

    /**
     * host name
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "host name")
    private String hostName;

    /**
     * port
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "port")
    private String port;

    /**
     * node type: ACTUAL or CONTAINER
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "node type: ACTUAL or CONTAINER")
    private Integer type;

    /**
     * launch date
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "launch date")
    private Date launchDate;

    /**
     * modified time
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "modified time")
    private Date modified;

    /**
     * created time
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "created time")
    private Date created;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", hostName=").append(hostName);
        sb.append(", port=").append(port);
        sb.append(", type=").append(type);
        sb.append(", launchDate=").append(launchDate);
        sb.append(", modified=").append(modified);
        sb.append(", created=").append(created);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}