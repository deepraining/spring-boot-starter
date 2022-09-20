package dr.sbs.mdbmbg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentGeneratorProperty {
  // 针对所有表的忽略字段集合
  public static List<String> commonIgnoreFields = new ArrayList<>();

  // 针对指定表的忽略字段集合（键为表名，与数据库中一致）
  public static Map<String, List<String>> specIgnoreFields = new HashMap<>();

  // 针对所有表的过滤名字
  public static String commonFilterName;

  // 针对指定表的过滤名字（键为表名，与数据库中一致）
  public static Map<String, String> specFilterName = new HashMap<>();

  static {
    // 密码字段不展示
    commonIgnoreFields.add("password");
  }
}
