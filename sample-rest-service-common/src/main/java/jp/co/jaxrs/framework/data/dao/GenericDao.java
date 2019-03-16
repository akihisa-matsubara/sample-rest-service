package jp.co.jaxrs.framework.data.dao;

import javax.persistence.EntityManager;

/**
 * mydb汎用Dao.
 *
 * @param <T> Entity
 */
public abstract class GenericDao<T> {

  /**
   * EntityManagerを取得します.
   *
   * @return EntityManager
   */
  public abstract EntityManager getEntityManager();

  /**
   * Select.
   *
   * @param entityClass Entity class
   * @param primaryKey Primary Key
   * @return result
   */
  public T find(Class<T> entityClass, Object primaryKey) {
    return getEntityManager().find(entityClass, primaryKey);
  }

  /**
   * Insert.
   *
   * @param entity Entity
   */
  public void create(T entity) {
    getEntityManager().persist(entity);
  }

  /**
   * Update.
   *
   * @param entity Entity
   */
  public void update(T entity) {
    getEntityManager().merge(entity);
  }

  /**
   * Delete.
   *
   * @param entity Entity
   */
  public void delete(T entity) {
    getEntityManager().remove(entity);
  }

}
