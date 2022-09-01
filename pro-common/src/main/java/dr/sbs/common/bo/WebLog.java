package dr.sbs.common.bo;

import lombok.Data;

/** Log wrapper class for Controller level */
@Data
public class WebLog {
  /** Operation description */
  private String description;

  /** Operation user */
  private String username;

  /** Operation start time */
  private Long startTime;

  /** Operation consume time */
  private Integer spendTime;

  /** Url base path */
  private String basePath;

  /** URI */
  private String uri;

  /** URL */
  private String url;

  /** HTTP method */
  private String method;

  /** IP address */
  private String ip;

  private Object parameter;

  private Object result;
}
