package dr.sbs.common.util;

/** 缓存键工具 */
public class SbsCacheKeyUtil {
  // 运行环境（灰度环境与产品环境的缓存要分开）
  public static String ENV = "";
  // 主版本号（针对所有的键，次版本号可以在每个键上另外加）
  public static final String VERSION = "v1";
  // 通用
  public static final String COMMON_DB = "sbsCommon";
  // 管理项目用
  public static final String ADMIN_DB = "sbsAdmin";
  // 前端项目用
  public static final String FRONT_DB = "sbsFront";

  public static final Integer ONE_MINUTE = 60;
  public static final Integer TWO_MINUTES = 2 * 60;
  public static final Integer THREE_MINUTES = 3 * 60;
  public static final Integer FIVE_MINUTES = 5 * 60;
  public static final Integer TEN_MINUTES = 10 * 60;
  public static final Integer TWENTY_MINUTES = 20 * 60;
  public static final Integer THIRTY_MINUTES = 30 * 60;
  public static final Integer ONE_HOUR = 3600;
  public static final Integer TWO_HOURS = 2 * 3600;
  public static final Integer THREE_HOURS = 3 * 3600;
  public static final Integer SIX_HOURS = 6 * 3600;
  public static final Integer TWELVE_HOURS = 12 * 3600;
  public static final Integer ONE_DAY = 24 * 3600;
  public static final Integer TWO_DAYS = 2 * 24 * 3600;
  public static final Integer THREE_DAYS = 3 * 24 * 3600;
  public static final Integer ONE_WEEK = 7 * 24 * 3600;
  public static final Integer TWO_WEEKS = 14 * 24 * 3600;
  public static final Integer ONE_MONTH = 30 * 24 * 3600;
  public static final Integer TWO_MONTHS = 2 * 30 * 24 * 3600;
  public static final Integer THREE_MONTHS = 3 * 30 * 24 * 3600;
  public static final Integer SIX_MONTHS = 6 * 30 * 24 * 3600;
  public static final Integer ONE_YEAR = 12 * 30 * 24 * 3600;

  public static String getCommonDbKey(Object... keys) {
    StringBuilder res = new StringBuilder(COMMON_DB);
    res.append(":").append(ENV).append(":").append(VERSION);
    for (Object key : keys) {
      res.append(":").append(key);
    }
    return res.toString();
  }

  public static String getAdminDbKey(Object... keys) {
    StringBuilder res = new StringBuilder(ADMIN_DB);
    res.append(":").append(ENV).append(":").append(VERSION);
    for (Object key : keys) {
      res.append(":").append(key);
    }
    return res.toString();
  }

  public static String getFrontDbKey(Object... keys) {
    StringBuilder res = new StringBuilder(FRONT_DB);
    res.append(":").append(ENV).append(":").append(VERSION);
    for (Object key : keys) {
      res.append(":").append(key);
    }
    return res.toString();
  }
}
