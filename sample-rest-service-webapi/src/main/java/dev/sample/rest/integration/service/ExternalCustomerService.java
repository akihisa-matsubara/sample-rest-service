package dev.sample.rest.integration.service;

import java.util.List;
import dev.sample.rest.common.dto.CustomerDto;

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
