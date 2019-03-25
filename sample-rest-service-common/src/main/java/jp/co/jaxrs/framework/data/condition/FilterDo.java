package jp.co.jaxrs.framework.data.condition;

import lombok.Builder;
import lombok.Data;

/**
 * フィルター設定DO.
 */
@Builder
@Data
public class FilterDo {

  /** offset 1から開始. */
  @Builder.Default
  private int offset = 0;

  /** 1リクエストあたりに応答する要素の件数. */
  @Builder.Default
  private int limit = 100;

  /** 全件数. */
  @Builder.Default
  private int total = -1;

}
