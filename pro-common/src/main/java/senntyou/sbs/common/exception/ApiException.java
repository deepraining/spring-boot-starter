package senntyou.sbs.common.exception;

import senntyou.sbs.common.IErrorCode;

// 自定义API异常
public class ApiException extends RuntimeException {
  private IErrorCode errorCode;

  public ApiException(IErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public ApiException(String message) {
    super(message);
  }

  public ApiException(Throwable cause) {
    super(cause);
  }

  public ApiException(String message, Throwable cause) {
    super(message, cause);
  }

  public IErrorCode getErrorCode() {
    return errorCode;
  }
}
