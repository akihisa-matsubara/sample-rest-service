package jp.co.sample.rest.framework.exception;

import jp.co.sample.rest.framework.exception.dto.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * システム基底例外.
 * 必須のパラメータが設定されていないなど、処理継続不可能な場合に送出する例外.
 * 主にアプリケーションの不具合に起因して発生する.
 */
@AllArgsConstructor
@Getter
public class SystemException extends RuntimeException {

  /** serialVersionUID. */
  private static final long serialVersionUID = -3994899668033929545L;

  /** エラーDto. */
  private final ErrorMessage errorDto;

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
  public SystemException(Throwable cause, ErrorMessage error) {
    super(cause);
    this.errorDto = error;
  }

}
