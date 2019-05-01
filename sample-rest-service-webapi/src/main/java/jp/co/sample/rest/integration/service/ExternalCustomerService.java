package jp.co.sample.rest.integration.service;

import jp.co.sample.rest.common.dto.CustomerDto;
import java.util.List;

/**
 * 外部顧客サービス.
 */
public interface ExternalCustomerService {

  /**
   * 顧客情報を取得します.
   *
   * @return 顧客情報({@link CustomerDto})のリスト、存在しない・エラーが発生した場合は空のリスト
   */
  List<CustomerDto> getCustomers();

}
