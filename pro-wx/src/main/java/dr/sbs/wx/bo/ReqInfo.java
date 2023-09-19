package dr.sbs.wx.bo;

import cn.hutool.json.JSONObject;
import java.util.Map;
import javax.servlet.http.Cookie;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqInfo {
  private String userAgent;
  private String lowerUserAgent;
  private String requestHost;
  private String requestPath;
  private String requestUrl;
  private String queryString;
  // 完整的链接
  private String requestLink;
  private JSONObject requestParams;
  // @RequestBody 获取后手动注入
  private JSONObject requestBody;
  private Map<String, String> headers;
  private Map<String, String> cookies;
  private Map<String, Cookie> cookieMap;
}
