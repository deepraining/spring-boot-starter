package dr.sbs.mbg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentGeneratorProperty {
  // 针对所有表的忽略字段集合，作用在属性上
  public static List<String> commonIgnoreFields = new ArrayList<>();

  // 针对指定表的忽略字段集合（键为表名，与数据库中一致），作用在属性上
  public static Map<String, List<String>> specIgnoreFields = new HashMap<>();

  // 针对所有表的过滤字段名字，作用在类上
  public static String commonFilterName;

  // 针对指定表的过滤字段名字（键为表名，与数据库中一致），作用在类上
  public static Map<String, String> specFilterName = new HashMap<>();

  static {
    // 密码字段不展示
    commonIgnoreFields.add("password");
  }
}
