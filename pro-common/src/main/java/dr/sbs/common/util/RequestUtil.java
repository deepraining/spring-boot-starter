package dr.sbs.common.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
  /** Get url basePath, scheme://domain:port */
  public static String getBasePath(HttpServletRequest request) {
    StringBuilder basePath = new StringBuilder();
    String scheme = request.getScheme();
    String domain = request.getServerName();
    int port = request.getServerPort();
    basePath.append(scheme);
    basePath.append("://");
    basePath.append(domain);
    if ("http".equalsIgnoreCase(scheme) && 80 != port) {
      basePath.append(":").append(String.valueOf(port));
    } else if ("https".equalsIgnoreCase(scheme) && port != 443) {
      basePath.append(":").append(String.valueOf(port));
    }
    return basePath.toString();
  }

  /** Get key-value map of parameters */
  public static Map<String, String> getParameterMap(HttpServletRequest request) {
    Map<String, String> result = new HashMap<>();
    Enumeration parameterNames = request.getParameterNames();
    while (parameterNames.hasMoreElements()) {
      String parameterName = (String) parameterNames.nextElement();
      result.put(parameterName, request.getParameter(parameterName));
    }
    return result;
  }
}
