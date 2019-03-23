package jp.co.jaxrs.sample.common.data.dao;

import javax.enterprise.context.ApplicationScoped;

/**
 * シーケンス生成Dao.
 */
@ApplicationScoped
public class SequenceGenerateDao extends MyDbDao<Integer, String> {
  private static final String SELECT_SEQ_CUSTOMER_NO = "SELECT NEXT VALUE FOR SEQ_CUSTOMER_NO FROM SYSIBM.DUAL";

  /**
   * 顧客番号シーケンス生成.
   *
   * @return 顧客番号シーケンス
   */
  public int generateCustomerNo() {
    return (Integer) getEntityManager().createNativeQuery(SELECT_SEQ_CUSTOMER_NO).getSingleResult();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<Integer> getEntityType() {
    return Integer.class;
  }

}
