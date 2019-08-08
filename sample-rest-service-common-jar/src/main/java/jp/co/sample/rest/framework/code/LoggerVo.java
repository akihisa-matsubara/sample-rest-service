package jp.co.sample.rest.framework.code;

import jp.co.sample.common.code.CodeVo;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ロガーVO.
 */
@AllArgsConstructor
@Getter
public enum LoggerVo implements CodeVo {

  /** パフォーマンスロガー. */
  PERFORMANCE_LOGGER("PERFORMANCE_LOGGER", "Performance Logger"),
  /** アクセスロガー. */
  ACCESS_LOGGER("ACCESS_LOGGER", "Access Logger"),
  /** 利用統計ロガー. */
  STATISTICS_LOGGER("STATISTICS_LOGGER", "Statistics Logger"),
  /** エラーロガー. */
  ERROR_LOGGER("ERROR_LOGGER", "Error Logger"),
  ;

  /** コード. */
  private String code;

  /** デコード. */
  private String decode;

}
