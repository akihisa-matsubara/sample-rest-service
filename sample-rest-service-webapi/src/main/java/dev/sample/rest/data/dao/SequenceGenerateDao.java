package dev.sample.rest.data.dao;

import javax.enterprise.context.ApplicationScoped;
import dev.sample.framework.core.data.dao.MyDbDao;

/**
 * シーケンス生成Dao.
 */
@ApplicationScoped
// PKはコンパイルエラー回避のためにStringを設定
public class SequenceGenerateDao extends MyDbDao<Integer, String> {
  private static final String GENERATE_CUSTOMER_NO = "SequenceGenerator.customerNo";

  /**
   * 顧客番号シーケンス生成.
   *
   * @return 顧客番号シーケンス
   */
  public int generateCustomerNo() {
    return (Integer) getEntityManager().createNamedQuery(GENERATE_CUSTOMER_NO).getSingleResult();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<Integer> getEntityType() {
    return Integer.class;
  }

}
