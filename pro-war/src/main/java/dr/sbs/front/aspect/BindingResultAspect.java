package dr.sbs.front.aspect;

import dr.sbs.common.CommonResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * global error handling aspect
 *
 * <p>note: `controller`的方法里，用`@Validated`标记需要验证的参数，最后面要有`BindingResult bindingResult`参数，才能生效
 */
@Aspect
@Component
@Order(2)
public class BindingResultAspect {
  @Pointcut("execution(public * dr.sbs.front.controller.*.*(..))")
  public void bindingResult() {
    //
  }

  @Around("bindingResult()")
  public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
    Object[] args = joinPoint.getArgs();
    for (Object arg : args) {
      if (arg instanceof BindingResult) {
        BindingResult result = (BindingResult) arg;
        if (result.hasErrors()) {
          FieldError fieldError = result.getFieldError();
          if (fieldError != null) {
            return CommonResult.validateFailed(fieldError.getDefaultMessage());
          } else {
            return CommonResult.validateFailed();
          }
        }
      }
    }
    return joinPoint.proceed();
  }
}
