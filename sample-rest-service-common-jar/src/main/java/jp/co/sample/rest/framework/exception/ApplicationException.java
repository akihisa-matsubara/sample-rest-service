package jp.co.sample.rest.framework.exception;

import jp.co.sample.rest.framework.pres.dto.ErrorDto;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * アプリケーション基底例外.
 */
@Getter
public class ApplicationException extends RuntimeException {

  /** serialVersionUID. */
  private static final long serialVersionUID = -3783009899660935057L;

  /** エラーDto. */
  private final List<ErrorDto> errorList;

  /**
   * デフォルトコンストラクター.
   *
   * @param cause 例外
   * @param error エラーDto
   */
  public ApplicationException(Throwable cause, ErrorDto error) {
    super(cause);
    errorList = new ArrayList<>();
    errorList.add(error);
  }

  /**
   * デフォルトコンストラクター.
   *
   * @param cause 例外
   * @param errorList エラーDtoのリスト
   */
  public ApplicationException(Throwable cause, List<ErrorDto> errorList) {
    super(cause);
    this.errorList = errorList;
  }
}
