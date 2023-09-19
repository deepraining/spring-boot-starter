package dr.sbs.wx.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import dr.sbs.wx.bo.ReqInfo;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class ServletUtil {
  public static JSONObject getRequestParams(HttpServletRequest request) {
    JSONObject requestParams = new JSONObject();

    // 包含GET:queryString(decode之后的)、POST:application/form-data、
    // POST:application/x-wwww-form-urlencoded，但不包括POST:application/json
    Map<String, String[]> parameterMap = request.getParameterMap();
    Set<String> parameterMapKeys = parameterMap.keySet();
    for (String name : parameterMapKeys) {
      String[] values = parameterMap.get(name);
      if (values.length > 0) requestParams.put(name, values[0]);
      else requestParams.put(name, "");
    }

    return requestParams;
  }

  public static ReqInfo getReqInfo(HttpServletRequest request) {
    ReqInfo reqInfo = new ReqInfo();

    String requestHost = request.getServerName();
    String requestPath = request.getRequestURI();
    String requestUrl = request.getRequestURL().toString();
    String queryString = request.getQueryString();
    String requestLink = requestUrl;
    if (!StringUtils.isEmpty(queryString)) requestLink += "?" + queryString;

    Map<String, String> headers = new HashMap<>();
    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String name = headerNames.nextElement();
      headers.put(name, request.getHeader(name));
    }

    JSONObject requestParams = getRequestParams(request);

    Map<String, String> cookies = new HashMap<>();
    Map<String, Cookie> cookieMap = new HashMap<>();
    Cookie[] cookieList = request.getCookies();
    if (cookieList != null) {
      for (Cookie cookie : cookieList) {
        String name = cookie.getName();
        cookieMap.put(name, cookie);
        cookies.put(name, cookie.getValue());
      }
    } else {
      log.info("请求无cookies");
    }

    String userAgent = "";
    String lowerUserAgent = "";
    if (headers.containsKey("User-Agent") || headers.containsKey("user-agent")) {
      userAgent =
          headers.containsKey("User-Agent") ? headers.get("User-Agent") : headers.get("user-agent");
      lowerUserAgent = userAgent.toLowerCase();
    } else {
      log.info("请求无userAgent");
    }

    reqInfo.setUserAgent(userAgent);
    reqInfo.setLowerUserAgent(lowerUserAgent);
    reqInfo.setRequestHost(requestHost);
    reqInfo.setRequestPath(requestPath);
    reqInfo.setRequestUrl(requestUrl);
    reqInfo.setQueryString(queryString);
    reqInfo.setRequestLink(requestLink);
    reqInfo.setRequestParams(requestParams);
    reqInfo.setHeaders(headers);
    reqInfo.setCookies(cookies);
    reqInfo.setCookieMap(cookieMap);

    log.info("reqInfo: " + JSONUtil.toJsonStr(reqInfo));

    return reqInfo;
  }

  public static void setCookie(HttpServletResponse response, String name, String value) {
    Cookie cookie = new Cookie(name, value);
    cookie.setPath("/");
    response.addCookie(cookie);
  }

  public static void setCookie(
      HttpServletResponse response, String name, String value, int expireSeconds) {
    Cookie cookie = new Cookie(name, value);
    cookie.setPath("/");
    cookie.setMaxAge(expireSeconds);
    response.addCookie(cookie);
  }

  public static void replaceCookie(
      HttpServletResponse response, String name, String value, Map<String, Cookie> cookieMap) {
    Cookie cookie = cookieMap.get(name);
    if (cookie == null) {
      setCookie(response, name, value);
      return;
    }
    cookie.setValue(value);
    response.addCookie(cookie);
  }

  public static void removeCookie(
      HttpServletResponse response, String name, String value, Map<String, Cookie> cookieMap) {
    Cookie cookie = cookieMap.get(name);
    if (cookie == null) {
      return;
    }
    cookie.setMaxAge(0);
    response.addCookie(cookie);
  }
}
