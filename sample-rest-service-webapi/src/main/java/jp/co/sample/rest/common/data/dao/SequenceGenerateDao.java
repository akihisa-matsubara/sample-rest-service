package jp.co.sample.rest.common.data.dao;

import jp.co.sample.rest.framework.data.dao.MyDbDao;
import javax.enterprise.context.ApplicationScoped;

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
