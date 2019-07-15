package senntyou.sbs.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class JsonUtil {
  private static final ObjectMapper MAPPER = new ObjectMapper();

  /** Transform Object to Json string */
  public static String objectToJson(Object data) {
    try {
      String string = MAPPER.writeValueAsString(data);
      return string;
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Transform Json string to Object
   *
   * @param jsonData Json string
   * @param beanType Object type
   */
  public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
    try {
      T t = MAPPER.readValue(jsonData, beanType);
      return t;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /** Transform Json string to List[Object] */
  public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
    JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
    try {
      List<T> list = MAPPER.readValue(jsonData, javaType);
      return list;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
}
