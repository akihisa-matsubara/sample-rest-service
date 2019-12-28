package jp.co.sample.rest.common.data.dao;

import jp.co.sample.framework.core.data.dao.MyDbDao;
import jp.co.sample.rest.common.data.entity.CustomerEntity;
import javax.enterprise.context.ApplicationScoped;

/**
 * 顧客Dao.
 */
@ApplicationScoped
public class CustomerDao extends MyDbDao<CustomerEntity, String> {

  /**
   * 主キー削除.
   *
   * @param customerNo 顧客番号
   */
  public void deleteById(String customerNo) {
    getEntityManager().createNamedQuery(CustomerEntity.DELETE_BY_ID)
      .setParameter("customerNo", customerNo)
      .executeUpdate();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<CustomerEntity> getEntityType() {
    return CustomerEntity.class;
  }
}
