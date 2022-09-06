package dr.sbs.front.aspect;

import dr.sbs.front.annotation.CacheException;
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

/** Redis缓存切面，防止Redis宕机影响正常业务逻辑 */
@Aspect
@Component
@Order(2)
public class RedisCacheAspect {
  private static Logger LOGGER = LoggerFactory.getLogger(RedisCacheAspect.class);

  @Pointcut("execution(public * dr.sbs.front.service.*CacheService.*(..))")
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
