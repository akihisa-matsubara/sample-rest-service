package jp.co.jaxrs.sample.common.data.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * mydb汎用Dao.
 *
 * @param <T> Entity
 */
public class GenericDao<T> {

  /** mydb EntityManager. */
  @PersistenceContext(unitName = "mydb")
  EntityManager entityManager;

  /**
   * Select.
   *
   * @param entityClass Entity class
   * @param primaryKey Primary Key
   * @return result
   */
  public T find(Class<T> entityClass, Object primaryKey) {
    return entityManager.find(entityClass, primaryKey);
  }

  /**
   * Insert.
   *
   * @param entity Entity
   */
  public void create(T entity) {
    entityManager.persist(entity);
  }

  /**
   * Update.
   *
   * @param entity Entity
   */
  public void update(T entity) {
    entityManager.merge(entity);
  }

  /**
   * Delete.
   *
   * @param entity Entity
   */
  public void delete(T entity) {
    entityManager.remove(entity);
  }

}
