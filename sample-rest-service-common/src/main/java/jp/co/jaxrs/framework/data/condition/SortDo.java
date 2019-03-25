package jp.co.jaxrs.framework.data.condition;

import lombok.Builder;
import lombok.Data;

/**
 * ソート順DO.
 */
@Builder
@Data
public class SortDo {

  /** ソート項目名. */
  private String field;

  /** 昇順. */
  @Builder.Default
  private boolean asc = true;

}
