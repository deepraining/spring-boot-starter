package dr.sbs.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class JsonUtil {
  private static final ObjectMapper MAPPER = new ObjectMapper();

  /** Transform Object to Json string */
  public static String objectToJson(Object data) {
    try {
      return MAPPER.writeValueAsString(data);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  /** Transform Json string to Object */
  public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
    try {
      return MAPPER.readValue(jsonData, beanType);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /** Transform Json string to List[Object] */
  public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
    JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
    try {
      return MAPPER.readValue(jsonData, javaType);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
}
