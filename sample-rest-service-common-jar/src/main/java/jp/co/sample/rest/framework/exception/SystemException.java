package jp.co.sample.rest.framework.exception;

import jp.co.sample.rest.framework.pres.dto.ErrorDto;
import lombok.Getter;

/**
 * システム基底例外.
 */
@Getter
public class SystemException extends RuntimeException {

  /** serialVersionUID. */
  private static final long serialVersionUID = -3994899668033929545L;

  /** エラーDto. */
  private final ErrorDto errorDto;

  /**
   * デフォルトコンストラクター.
   *
   * @param cause 例外
   */
  public SystemException(Throwable cause) {
    this(cause, null);
  }

  /**
   * デフォルトコンストラクター.
   *
   * @param cause 例外
   * @param error エラーDto
   */
  public SystemException(Throwable cause, ErrorDto error) {
    super(cause);
    this.errorDto = error;
  }

}
