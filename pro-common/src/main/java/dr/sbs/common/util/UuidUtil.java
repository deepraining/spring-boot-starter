package dr.sbs.common.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class UuidUtil {
  private static Snowflake snowflake;

  public static void init(long workerId, long dataCenterId) {
    if (snowflake == null) {
      snowflake = IdUtil.createSnowflake(workerId, dataCenterId);
    }
  }

  public static long nextId() {
    return snowflake.nextId();
  }
}
