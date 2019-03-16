package jp.co.jaxrs.sample.common.data.dao;

import jp.co.jaxrs.framework.data.dao.GenericDao;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;

/**
 * mydb汎用Dao.
 *
 * @param <T> Entity
 * @param <P> Primary Key
 */
@Getter
public abstract class MyDbDao<T, P extends Serializable> extends GenericDao<T, Serializable> {

  /** EntityManager. */
  @PersistenceContext(unitName = "mydb")
  private EntityManager entityManager;

}
