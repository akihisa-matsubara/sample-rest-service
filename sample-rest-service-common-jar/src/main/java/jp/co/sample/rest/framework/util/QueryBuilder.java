package jp.co.sample.rest.framework.util;

import jp.co.sample.rest.framework.data.condition.FilterDo;
import jp.co.sample.rest.framework.data.condition.SearchConditionDo;
import jp.co.sample.rest.framework.data.condition.SortDo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * クエリビルダー.
 */
@UtilityClass
public class QueryBuilder {

  /**
   * クエリパラメータMapにパラメーターを格納する.
   *
   * @param queryParams クエリパラメータMap
   * @param column 項目
   * @param value 値
   */
  public static void putParam(Map<String, Object> queryParams, String column, Object value) {
    if (value != null) {
      queryParams.put(column, value);
    }
  }

  /**
   * 検索条件DOの作成.
   *
   * @param offset Offset
   * @param limit 取得件数
   * @param sort ソート条件
   * @param queryParams クエリパラメータMap
   * @return {@link SearchConditionDo} 検索条件DO
   */
  public static SearchConditionDo createSearchCondition(
      int offset,
      int limit,
      String sort,
      Map<String, Object> queryParams) {
    return SearchConditionDo.builder()
        .filter(FilterDo.builder().offset(offset).limit(limit).build())
        .sortList(createSortDos(sort))
        .queryParams(queryParams)
        .build();
  }

  /**
   * 検索件数取得クエリ、検索結果取得クエリを構築します.
   *
   * @param <E> Entity
   * @param entity entity
   * @param searchCondition {@link SearchConditionDo} 検索条件DO
   */
  public static <E> void buildQuery(Class<E> entity, SearchConditionDo searchCondition) {
    String entityName = entity.getSimpleName();
    String where = buildWhere(searchCondition.getQueryParams());

    // 検索件数取得クエリ構築
    StringBuilder countQuery = new StringBuilder();
    countQuery.append("SELECT COUNT(e) FROM ").append(entityName).append(" e ");
    if (!StringUtils.isEmpty(where)) {
      countQuery.append(" WHERE ").append(where);
    }
    searchCondition.setCountQuery(countQuery.toString());

    // 検索結果取得クエリ構築
    StringBuilder searchQuery = new StringBuilder();
    searchQuery.append("SELECT e FROM ").append(entityName).append(" e ");
    if (!StringUtils.isEmpty(where)) {
      searchQuery.append(" WHERE ").append(where);
    }
    String orderBy = buildOrderBy(searchCondition.getSortList());
    if (!StringUtils.isEmpty(orderBy)) {
      searchQuery.append(orderBy);
    }
    searchCondition.setSearchQuery(searchQuery.toString());
  }

  /**
   * ソート順DOの作成.
   * fieldName[:asc or desc],fieldName...形式のソート文字列を分解し、ソート順DOを作成します.
   *
   * @param sortStr ソート文字列
   * @return {@link SortDo}のリスト
   */
  private static List<SortDo> createSortDos(String sortStr) {
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

  /**
   * WHERE節を構築します.
   *
   * @param queryParams クエリパラメータMap
   * @return WHERE節(WHERE句は含みません)
   */
  private static String buildWhere(Map<String, Object> queryParams) {
    if (queryParams == null || queryParams.isEmpty()) {
      return StringUtils.EMPTY;
    }
    StringBuilder where = new StringBuilder();
    for (Entry<String, Object> param : queryParams.entrySet()) {
      if (0 < where.length()) {
        where.append("AND ");
      }
      where.append("e.").append(param.getKey()).append(" = :").append(param.getKey()).append(" ");
    }
    return where.toString();
  }

  /**
   * ORDER BY節を構築します.
   *
   * @param sortList ソート順({@link SortDo})のリスト
   * @return ORDER BY節
   */
  private static String buildOrderBy(List<SortDo> sortList) {
    if (sortList == null || sortList.isEmpty()) {
      return StringUtils.EMPTY;
    }
    StringBuilder orderBy = new StringBuilder();
    sortList.forEach(sort -> {
      if (0 < orderBy.length()) {
        orderBy.append(", ");
      }
      orderBy.append("e").append(".").append(sort.getField()).append(sort.isAsc() ? StringUtils.EMPTY : " DESC");
    });
    return orderBy.insert(0, " ORDER BY ").toString();
  }

}
