package dr.sbs.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> {
  private long code;
  private String message;
  private T data;

  /**
   * Succeeded
   *
   * @param data Data to return
   */
  public static <T> CommonResult<T> success(T data) {
    return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
  }

  /**
   * Succeeded
   *
   * @param data Data to return
   * @param message Tip message
   */
  public static <T> CommonResult<T> success(T data, String message) {
    return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, data);
  }

  /** Failed */
  public static <T> CommonResult<T> failed(IErrorCode errorCode) {
    return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
  }

  /** Failed */
  public static <T> CommonResult<T> failed() {
    return new CommonResult<T>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), null);
  }

  /** Failed */
  public static <T> CommonResult<T> failed(String message) {
    return new CommonResult<T>(ResultCode.FAILED.getCode(), message, null);
  }

  /** Check parameters failed */
  public static <T> CommonResult<T> validateFailed() {
    return new CommonResult<T>(
        ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null);
  }

  /** Check parameters failed */
  public static <T> CommonResult<T> validateFailed(String message) {
    return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
  }

  /** Not logged in or token is expired */
  public static <T> CommonResult<T> unauthorized() {
    return new CommonResult<T>(
        ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), null);
  }

  /** Not logged in or token is expired */
  public static <T> CommonResult<T> unauthorized(String message) {
    return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), message, null);
  }

  /** No privileges */
  public static <T> CommonResult<T> forbidden() {
    return new CommonResult<T>(
        ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), null);
  }

  /** No privileges */
  public static <T> CommonResult<T> forbidden(String message) {
    return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), message, null);
  }
}
