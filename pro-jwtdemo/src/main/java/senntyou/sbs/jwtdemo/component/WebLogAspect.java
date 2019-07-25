package senntyou.sbs.jwtdemo.component;

import io.swagger.annotations.ApiOperation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.Markers;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import senntyou.sbs.common.bo.WebLog;
import senntyou.sbs.common.util.JsonUtil;
import senntyou.sbs.common.util.RequestUtil;

/** global log handling aspect */
@Aspect
@Component
@Order(1)
@Slf4j
public class WebLogAspect {
  private ThreadLocal<Long> startTime = new ThreadLocal<>();

  @Pointcut("execution(public * senntyou.sbs.jwtdemo.controller.*.*(..))")
  public void webLog() {
    //
  }

  @Before("webLog()")
  public void doBefore(JoinPoint joinPoint) throws Throwable {
    startTime.set(System.currentTimeMillis());
  }

  @AfterReturning(value = "webLog()", returning = "ret")
  public void doAfterReturning(Object ret) throws Throwable {
    //
  }

  @Around("webLog()")
  public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
    // Get current request object
    ServletRequestAttributes attributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();

    // Record request information
    WebLog webLog = new WebLog();
    Object result = joinPoint.proceed();
    Signature signature = joinPoint.getSignature();
    MethodSignature methodSignature = (MethodSignature) signature;
    Method method = methodSignature.getMethod();
    if (method.isAnnotationPresent(ApiOperation.class)) {
      ApiOperation log = method.getAnnotation(ApiOperation.class);
      webLog.setDescription(log.value());
    }
    long endTime = System.currentTimeMillis();
    webLog.setBasePath(RequestUtil.getBasePath(request));
    webLog.setIp(request.getRemoteUser());
    webLog.setMethod(request.getMethod());
    webLog.setParameter(getParameter(method, joinPoint.getArgs()));
    webLog.setResult(result);
    webLog.setSpendTime((int) (endTime - startTime.get()));
    webLog.setStartTime(startTime.get());
    webLog.setUri(request.getRequestURI());
    webLog.setUrl(request.getRequestURL().toString());
    Map<String, Object> logMap = new HashMap<>();
    logMap.put("url", webLog.getUrl());
    logMap.put("method", webLog.getMethod());
    logMap.put("parameter", webLog.getParameter());
    logMap.put("spendTime", webLog.getSpendTime());
    logMap.put("description", webLog.getDescription());
    log.info(Markers.appendEntries(logMap), JsonUtil.objectToJson(webLog));
    return result;
  }

  /** Get parameters by HTTP method and input arguments */
  private Object getParameter(Method method, Object[] args) {
    List<Object> argList = new ArrayList<>();
    Parameter[] parameters = method.getParameters();
    for (int i = 0; i < parameters.length; i++) {
      RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
      if (requestBody != null) {
        argList.add(args[i]);
      }
      RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
      if (requestParam != null) {
        Map<String, Object> map = new HashMap<>();
        String key = parameters[i].getName();
        if (!StringUtils.isEmpty(requestParam.value())) {
          key = requestParam.value();
        }
        map.put(key, args[i]);
        argList.add(map);
      }
    }
    if (argList.size() == 0) {
      return null;
    } else if (argList.size() == 1) {
      return argList.get(0);
    } else {
      return argList;
    }
  }
}
