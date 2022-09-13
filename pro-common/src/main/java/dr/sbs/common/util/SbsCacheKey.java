package dr.sbs.common.util;

/**
 * 缓存键定义
 *
 * <p>注意：当上一个版本的缓存内容与这一个版本的缓存内容不兼容时，缓存键应增加一个版本，如 `V2`, `V3`
 */
public class SbsCacheKey {
  /** admin */
  // 示例
  public static final String ADMIN_EXAMPLE = "example";

  /** front */
  // 示例
  public static final String FRONT_EXAMPLE = "example";
  // 用户信息
  public static final String FRONT_USER = "user";
  // 微信用户信息
  public static final String FRONT_WX_USER = "wxUser";
  public static final String FRONT_WX_USER_BY_ID = "wxUserById";

  /** common */
  // 前端用户数据库行
  public static final String COMMON_FRONT_USER_ROW = "frontUserRow";
  // 文章数据库行
  public static final String COMMON_ARTICLE_ROW = "articleRow";
}
