package jp.co.sample.rest.framework.data.entitymanager;

import jp.co.sample.rest.framework.constant.PersistenceUnit;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EntityManagerプロデューサークラス.
 */
@ApplicationScoped
public class EntityManagerProducer {

  /** EntityManager. */
  @PersistenceContext(unitName = PersistenceUnit.MYDB)
  private EntityManager myDbEntityManager;

  /**
   * MYDB用のEntityManagerを取得します.
   *
   * @param ip インジェクションポイント
   * @return MYDB用のEntityManager
   */
  @Produces
  @MyDb
  @Dependent
  public EntityManager getMyDbEntityManager(InjectionPoint ip) {
    return myDbEntityManager;
  }

}
