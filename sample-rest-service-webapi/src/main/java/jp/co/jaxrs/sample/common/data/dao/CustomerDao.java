package jp.co.jaxrs.sample.common.data.dao;

import jp.co.jaxrs.sample.common.data.entity.CustomerEntity;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

/**
 * 顧客Dao.
 */
@ApplicationScoped
public class CustomerDao extends MyDbDao<CustomerEntity, String> {

  /**
   * 全件検索.
   *
   * @return 検索結果. 存在しない場合は空のリスト
   */
  @SuppressWarnings("unchecked")
  public List<CustomerEntity> findAll() {
    return (List<CustomerEntity>) getEntityManager().createNamedQuery(CustomerEntity.FIND_ALL).getResultList();
  }

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
