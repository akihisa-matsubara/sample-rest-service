package jp.co.jaxrs.framework.exception;

import jp.co.jaxrs.framework.pres.dto.ErrorDto;
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
   * @param errorDto エラーDto
   */
  public SystemException(ErrorDto errorDto) {
    this.errorDto = errorDto;
  }
}
