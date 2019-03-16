package jp.co.jaxrs.framework.exception;

import jp.co.jaxrs.framework.pres.dto.ErrorDto;
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
   * @param errorList エラーDtoのリスト
   */
  public ApplicationException(List<ErrorDto> errorList) {
    this.errorList = errorList;
  }
}
