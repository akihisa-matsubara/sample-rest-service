package jp.co.sample.rest.framework.util;

import jp.co.sample.rest.framework.data.condition.SearchConditionDo;
import jp.co.sample.rest.framework.data.condition.SortDo;
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
