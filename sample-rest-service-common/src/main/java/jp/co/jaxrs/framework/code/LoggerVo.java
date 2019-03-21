package jp.co.jaxrs.framework.code;

import lombok.Getter;

/**
 * ロガーVO.
 */
@Getter
public enum LoggerVo implements CodeVo {

  /** パフォーマンスロガー. */
  PERFORMANCE_LOGGER("PERFORMANCE_LOGGER", "Performance Log"),
  /** アクセスロガー. */
  ACCESS_LOGGER("ACCESS_LOGGER", "Access Log"),
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
  private LoggerVo(String code, String decode) {
    this.code = code;
    this.decode = decode;
  }

}
