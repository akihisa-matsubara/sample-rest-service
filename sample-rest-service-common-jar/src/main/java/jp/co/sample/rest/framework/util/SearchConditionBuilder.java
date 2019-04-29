package jp.co.sample.rest.framework.util;

import jp.co.sample.rest.framework.data.condition.FilterDo;
import jp.co.sample.rest.framework.data.condition.SearchConditionDo;
import jp.co.sample.rest.framework.data.condition.SortDo;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 * 検索条件DOビルダー.
 */
public class SearchConditionBuilder {

  /** Offset. */
  private int offset;

  /** 取得件数. */
  private int limit;

  /** ソート条件. */
  private String sort;

  /** クエリパラメータMap. */
  private Map<String, Object> queryParams = new LinkedHashMap<>();

  /**
   * デフォルトコンストラクタ.
   *
   * @param offset Offset
   * @param limit 取得件数
   * @param sort ソート条件
   */
  public SearchConditionBuilder(int offset, int limit, String sort) {
    this.offset = offset;
    this.limit = limit;
    this.sort = sort;
  }

  /**
   * クエリパラメータMapにパラメーターを格納する.
   * 格納順番がそのままwhere節の出力順となる為、アクセスパスを考慮して格納すること.
   *
   * @param column 項目
   * @param value 値
   * @return クエリビルダー
   */
  public SearchConditionBuilder putParam(String column, Object value) {
    if (value != null) {
      queryParams.put(column, value);
    }
    return this;
  }

  /**
   * 検索条件DOの作成.
   *
   * @return {@link SearchConditionDo} 検索条件DO
   */
  public SearchConditionDo build() {
    return SearchConditionDo.builder()
        .filter(FilterDo.builder().offset(offset).limit(limit).build())
        .sortList(createSortDos(sort))
        .queryParams(queryParams)
        .build();
  }

  /**
   * ソート順DOの作成.
   * fieldName[:asc or desc],fieldName...形式のソート文字列を分解し、ソート順DOを作成します.
   *
   * @param sortStr ソート文字列
   * @return {@link SortDo}のリスト
   */
  private List<SortDo> createSortDos(String sortStr) {
    List<SortDo> sortDoList = new ArrayList<>();
    sortStr = StringUtils.remove(sortStr, StringUtils.SPACE);

    if (StringUtils.isEmpty(sortStr)) {
      return sortDoList;
    }

    for (String sortPhrase : StringUtils.split(sortStr, ",")) {
      String[] sortWords = StringUtils.split(sortPhrase, ":");
      if (sortWords.length == 1) {
        sortDoList.add(SortDo.builder().field(sortWords[0]).build());

      } else {
        sortDoList.add(SortDo.builder().field(sortWords[0]).asc(!"desc".equalsIgnoreCase(sortWords[1])).build());

      }
    }

    return sortDoList;
  }

}