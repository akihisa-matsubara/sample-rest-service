package jp.co.sample.rest.framework.data.condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

/**
 * 検索条件DO.
 */
@Builder
@Data
public class SearchConditionDo {

  /** フィルターDO. */
  @Builder.Default
  private FilterDo filter = FilterDo.builder().build();

  /** ソートキーのリスト. */
  @Builder.Default
  private List<SortDo> sortList = new ArrayList<>();

  /** クエリパラメータMap. */
  @Builder.Default
  private Map<String, Object> queryParams = new HashMap<>();

  /** 検索件数を取得するためのクエリ. */
  private String countQuery;

  /** 検索結果を取得するためのクエリ. */
  private String searchQuery;

}
