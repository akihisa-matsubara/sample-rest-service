package jp.co.jaxrs.framework.data.dao;

import jp.co.jaxrs.framework.data.condition.SearchConditionDo;
import jp.co.jaxrs.framework.util.QueryBuilder;
import jp.co.jaxrs.framework.util.ReflectionUtils;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EmbeddedId;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Query;

/**
 * mydb汎用Dao.
 *
 * @param <E> Entity
 * @param <PK> Primary Key
 */
public abstract class GenericDao<E, PK extends Serializable> {

  /**
   * EntityManagerを取得します.
   *
   * @return {@link EntityManager} EntityManager
   */
  public abstract EntityManager getEntityManager();

  /**
   * Select.
   *
   * @param primaryKey Primary Key
   * @return entity instance. 存在しない場合はnull
   */
  public E find(PK primaryKey) {
    return getEntityManager().find(getEntityType(), primaryKey);
  }

  /**
   * Insert.
   * （PKを自動採番する場合はDB反映し値を確定させる）
   *
   * @param entity Entity
   * @return PK. PKが存在しない場合はnull
   */
  public PK create(E entity) {
    getEntityManager().persist(entity);

    if (isGeneratedValue(entity)) {
      getEntityManager().flush();
    }

    PK pk = ReflectionUtils.getAnnotatedField(Id.class, entity);
    if (pk != null) {
      return pk;
    }

    return ReflectionUtils.getAnnotatedField(EmbeddedId.class, entity);
  }

  /**
   * Update.
   *
   * @param entity Entity
   */
  public void update(E entity) {
    getEntityManager().merge(entity);
  }

  /**
   * Delete.
   *
   * @param entity Entity
   */
  public void delete(E entity) {
    getEntityManager().remove(entity);
  }

  /**
   * Entityの型を取得します.
   *
   * @return entity type
   */
  public abstract Class<E> getEntityType();

  /**
   * 検索条件に従い検索結果を取得します.
   * 開始行から指定件数を検索します.
   *
   * @param condition 検索条件
   * @return 検索結果
   */
  @SuppressWarnings("unchecked")
  public List<E> search(SearchConditionDo condition) {
    QueryBuilder.buildQuery(getEntityType(), condition);

    Query countQuery = getEntityManager().createQuery(condition.getCountQuery());
    condition.getQueryParams().forEach(countQuery::setParameter);
    int count = ((Long) countQuery.getSingleResult()).intValue();
    condition.getFilter().setTotal(count);

    Query searchQuery = getEntityManager().createQuery(condition.getSearchQuery());
    condition.getQueryParams().forEach(searchQuery::setParameter);
    searchQuery.setFirstResult(condition.getFilter().getOffset());
    searchQuery.setMaxResults(condition.getFilter().getLimit());
    return searchQuery.getResultList();
  }

  /**
   * DBによる自動採番を行うカラムが存在するか確認します.
   *
   * @param entity Entity
   * @return 自動採番を行うカラムが存在する場合はtrue
   */
  private boolean isGeneratedValue(E entity) {
    return Arrays.stream(entity.getClass().getDeclaredFields())
        .anyMatch(field -> field.getAnnotation(Id.class) != null && field.getAnnotation(GeneratedValue.class) != null);
  }

}
