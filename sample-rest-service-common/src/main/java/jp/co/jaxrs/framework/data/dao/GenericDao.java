package jp.co.jaxrs.framework.data.dao;

import jp.co.jaxrs.framework.util.ReflectionUtils;
import java.io.Serializable;
import java.util.Arrays;
import javax.persistence.EmbeddedId;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * mydb汎用Dao.
 *
 * @param <T> Entity
 */
public abstract class GenericDao<T, P extends Serializable> {

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
   * （PKを自動採番する場合はDB反映し値を確定させる）
   *
   * @param entity Entity
   * @return PK. PKが存在しない場合はnull
   */
  public P create(T entity) {
    getEntityManager().persist(entity);

    if (isGeneratedValue(entity)) {
      getEntityManager().flush();
    }

    P pk = ReflectionUtils.getAnnotatedField(Id.class, entity);
    if (pk != null) {
      return pk;
    }

    return ReflectionUtils.getAnnotatedField(EmbeddedId.class,entity);
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

  /**
   * DBによる自動採番を行うカラムが存在するか確認します.
   *
   * @param entity Entity
   * @return 自動採番を行うカラムが存在する場合はtrue
   */
  private boolean isGeneratedValue(T entity) {
    return Arrays.stream(entity.getClass().getDeclaredFields())
        .anyMatch(field -> field.getAnnotation(Id.class) != null && field.getAnnotation(GeneratedValue.class) != null);
  }
}
