package dr.sbs.protogen;

import cn.hutool.core.io.FileUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Generator {
  // 需要生成的包名
  private static String packageName = "dr.sbs.protogen";
  // 生成的文件名
  private static String fileName = "ProtoGen.proto";
  // 表名统一添加后缀
  private static String tableNameSuffix = "Proto";
  // 生成的文件目录，可添加多个(相对于根目录)
  private static List<String> dirNames = Arrays.asList("pro-proto/src/main/proto");

  public static void main(String[] args) throws IOException {
    String proDir = System.getProperty("user.dir");
    String rootDir = new File(proDir).getParent();

    Properties properties = new Properties();
    InputStream in = Generator.class.getResourceAsStream("/mysql.properties");
    properties.load(in);

    // proto 内容
    StringBuilder contentBuilder = new StringBuilder();
    contentBuilder.append("syntax = \"proto3\";\n\npackage ").append(packageName).append(";\n\n");

    Connection conn = null;
    try {
      String driverClassName = properties.getProperty("driverClassName");
      String url = properties.getProperty("url");
      String username = properties.getProperty("username");
      String password = properties.getProperty("password");

      // 注册 JDBC 驱动
      Class.forName(driverClassName);

      // 打开链接
      System.out.println("连接数据库...");
      conn = DriverManager.getConnection(url, username, password);

      // 执行查询
      System.out.println("实例化Statement对象...");
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("show tables");
      System.out.println("查询所有表...");
      // 展开结果集数据库
      while (rs.next()) {
        String tableName = rs.getString("Tables_in_starter");
        System.out.println("查询" + tableName + "表字段...");
        contentBuilder
            .append("message ")
            .append(underlineToCamel(tableName, false))
            .append(tableNameSuffix)
            .append(" {\n");

        Statement stmt2 = conn.createStatement();
        ResultSet rs2 = stmt2.executeQuery("describe " + tableName);

        int idx = 10;
        while (rs2.next()) {
          String fieldName = rs2.getString("Field");
          String fieldType = rs2.getString("Type");

          contentBuilder
              .append("  ")
              .append(mysqlToProtoType(fieldType))
              .append(" ")
              .append(underlineToCamel(fieldName, true))
              .append(" = ")
              .append(idx)
              .append(";\n");
          idx += 10;
        }

        contentBuilder.append("}\n\n");

        rs2.close();
        stmt2.close();
      }
      // 完成后关闭
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      // 处理 JDBC 错误
      e.printStackTrace();
    } finally {
      // 关闭资源
      try {
        if (conn != null) conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    for (String dirName : dirNames) {
      String filePath = rootDir + "/" + dirName + "/" + fileName;
      FileUtil.mkdir(dirName);
      FileUtil.writeString(contentBuilder.toString(), filePath, "UTF-8");

      System.out.println("生成proto文件:" + filePath);
    }

    System.out.println("done!");
  }

  /**
   * 下划线转驼峰法
   *
   * @param line 源字符串
   * @param smallCamel 大小驼峰，是否为小驼峰
   * @return 转换后的字符串
   */
  public static String underlineToCamel(String line, boolean smallCamel) {
    if (line == null || "".equals(line)) {
      return "";
    }
    StringBuffer sb = new StringBuffer();
    Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
    Matcher matcher = pattern.matcher(line);
    while (matcher.find()) {
      String word = matcher.group();
      sb.append(
          smallCamel && matcher.start() == 0
              ? Character.toLowerCase(word.charAt(0))
              : Character.toUpperCase(word.charAt(0)));
      int index = word.lastIndexOf('_');
      if (index > 0) {
        sb.append(word.substring(1, index).toLowerCase());
      } else {
        sb.append(word.substring(1).toLowerCase());
      }
    }
    return sb.toString();
  }

  /**
   * "int": "int32", "tinyint": "int32", "smallint": "int32", "mediumint": "int32", "bigint":
   * "int64", "varchar": "string", "timestamp": "string", "date": "string", "datetime": "string",
   * "text": "string", "double": "double", "decimal": "double", "float": "float",
   */
  private static Map<String, String> mysqlProtoTypeMap = new HashMap<>();

  static {
    mysqlProtoTypeMap.put("int", "int32");
    mysqlProtoTypeMap.put("tinyint", "int32");
    mysqlProtoTypeMap.put("smallint", "int32");
    mysqlProtoTypeMap.put("mediumint", "int32");
    mysqlProtoTypeMap.put("bigint", "int64");
    mysqlProtoTypeMap.put("varchar", "string");
    mysqlProtoTypeMap.put("timestamp", "string");
    mysqlProtoTypeMap.put("date", "string");
    mysqlProtoTypeMap.put("datetime", "string");
    mysqlProtoTypeMap.put("text", "string");
    mysqlProtoTypeMap.put("double", "double");
    mysqlProtoTypeMap.put("decimal", "double");
    mysqlProtoTypeMap.put("float", "float");
  }

  /**
   * mysql 数据类型转 proto 数据类型
   *
   * @param mysqlType mysql 数据类型
   * @return proto 数据类型
   */
  public static String mysqlToProtoType(String mysqlType) throws Exception {
    // 去掉括号
    String pureMysqlType = mysqlType.split("\\(")[0];
    String protoType = mysqlProtoTypeMap.get(pureMysqlType);
    if (protoType == null) throw new Exception("未识别mysql字段:" + mysqlType);
    return protoType;
  }
}
