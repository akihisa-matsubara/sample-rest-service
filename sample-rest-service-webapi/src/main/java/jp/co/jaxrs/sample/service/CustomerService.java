package jp.co.jaxrs.sample.service;

import jp.co.jaxrs.sample.data.entity.CustomerEntity;
import jp.co.jaxrs.sample.provider.requestdto.CustomerDto;
import java.util.List;

/**
 * 顧客サービス.
 */
public interface CustomerService {

  /**
   * 顧客情報を取得します.
   *
   * @return 顧客情報
   */
  List<CustomerEntity> getCustomers();

  /**
   * 指定した顧客情報を取得します.
   *
   * @param customerNo 顧客番号
   * @return 顧客情報
   */
  CustomerEntity getCustomer(String customerNo);

  /**
   * 顧客情報を作成します.
   *
   * @param form 顧客情報
   */
  void createCustomer(CustomerDto form);

  /**
   * 顧客情報を更新します.
   *
   * @param form 顧客情報
   * @return 更新件数
   */
  int updateCustomer(CustomerDto form);

  /**
   * 指定した顧客情報を削除します.
   *
   * @param customerNo 顧客番号
   */
  void deleteCustomer(String customerNo);

}
