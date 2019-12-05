package senntyou.sbs.common.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class UuidUtil {
  private static Snowflake snowflake = IdUtil.createSnowflake(1, 1);

  public static long nextId() {
    return snowflake.nextId();
  }
}
