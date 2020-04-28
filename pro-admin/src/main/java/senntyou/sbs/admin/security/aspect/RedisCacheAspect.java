package senntyou.sbs.admin.security.aspect;

import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import senntyou.sbs.admin.security.annotation.CacheException;

/** Redis缓存切面，防止Redis宕机影响正常业务逻辑 */
@Aspect
@Component
@Order(2)
public class RedisCacheAspect {
  private static Logger LOGGER = LoggerFactory.getLogger(RedisCacheAspect.class);

  @Pointcut(
      "execution(public * senntyou.sbs.admin.service.*CacheService.*(..)) || execution(public * senntyou.sbs.demo.service.*CacheService.*(..))")
  public void cacheAspect() {}

  @Around("cacheAspect()")
  public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
    Signature signature = joinPoint.getSignature();
    MethodSignature methodSignature = (MethodSignature) signature;
    Method method = methodSignature.getMethod();
    Object result = null;
    try {
      result = joinPoint.proceed();
    } catch (Throwable throwable) {
      // 有CacheException注解的方法需要抛出异常
      if (method.isAnnotationPresent(CacheException.class)) {
        throw throwable;
      } else {
        LOGGER.error(throwable.getMessage());
      }
    }
    return result;
  }
}
