package jp.co.jaxrs.sample.common.data.dao;

import jp.co.jaxrs.framework.data.dao.GenericDao;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;

/**
 * mydb汎用Dao.
 *
 * @param <E> Entity
 * @param <PK> Primary Key
 */
@Getter
public abstract class MyDbDao<E, PK extends Serializable> extends GenericDao<E, PK> {

  /** EntityManager. */
  @PersistenceContext(unitName = "mydb")
  private EntityManager entityManager;

}
