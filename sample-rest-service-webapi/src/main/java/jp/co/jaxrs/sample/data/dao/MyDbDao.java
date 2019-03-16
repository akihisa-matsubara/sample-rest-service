package jp.co.jaxrs.sample.data.dao;

import jp.co.jaxrs.sample.common.data.dao.GenericDao;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;

/**
 * mydb汎用Dao.
 *
 * @param <T> Entity
 */
@Getter
public abstract class MyDbDao<T> extends GenericDao<T> {

  /** mydb EntityManager. */
  @PersistenceContext(unitName = "mydb")
  private EntityManager entityManager;

}
