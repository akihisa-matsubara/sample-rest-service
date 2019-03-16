package jp.co.jaxrs.sample.data.dao;

import jp.co.jaxrs.sample.data.entity.CustomerEntity;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

/**
 * 顧客Dao.
 */
@ApplicationScoped
public class CustomerDao extends MyDbDao<CustomerEntity> {

  /**
   * 全件検索.
   *
   * @return 検索結果
   */
  @SuppressWarnings("unchecked")
  public List<CustomerEntity> findAll() {
    return (List<CustomerEntity>) getEntityManager().createNamedQuery(CustomerEntity.FIND_ALL).getResultList();
  }

  /**
   * 主キー検索.
   *
   * @param customerNo 顧客番号
   * @return 検索結果
   */
  public CustomerEntity findById(String customerNo) {
    return super.find(CustomerEntity.class, customerNo);
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
}
