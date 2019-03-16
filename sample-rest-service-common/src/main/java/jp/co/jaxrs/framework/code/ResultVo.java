package jp.co.jaxrs.framework.code;

import lombok.Getter;

/**
 * 結果VO.
 */
@Getter
public enum ResultVo implements CodeVo {

  /** Success. */
  SUCCESS("0", "Success"),
  /** Failure. */
  FAILURE("1", "Failure"),
  ;

  /** コード. */
  private String code;

  /** デコード. */
  private String decode;

  /**
   * デフォルトコンストラクタ.
   *
   * @param code コード
   * @param decode デコード
   */
  private ResultVo(String code, String decode) {
    this.code = code;
    this.decode = decode;
  }

}
