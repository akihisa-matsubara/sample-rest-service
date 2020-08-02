package dev.sample.rest.biz.logic;

import dev.sample.framework.core.data.condition.SearchConditionDo;
import dev.sample.rest.common.dto.CustomerDto;
import java.util.List;

/**
 * 顧客サービス.
 */
public interface CustomerService {

  /**
   * 顧客情報を取得します.
   *
   * @param searchCondition {@link SearchConditionDo} 検索条件DO
   * @return 顧客情報({@link CustomerDto})のリスト、存在しない場合は空のリスト
   */
  List<CustomerDto> getCustomers(SearchConditionDo searchCondition);

  /**
   * 指定した顧客情報を取得します.
   *
   * @param customerNo 顧客番号
   * @return {@link CustomerDto} 顧客情報、存在しない場合はnull
   */
  CustomerDto getCustomer(String customerNo);

  /**
   * 顧客情報を作成します.
   *
   * @param dto {@link CustomerDto} 顧客情報
   */
  void createCustomer(CustomerDto dto);

  /**
   * 顧客情報を更新します.
   *
   * @param dto {@link CustomerDto} 顧客情報
   * @return 更新件数
   */
  int updateCustomer(CustomerDto dto);

  /**
   * 指定した顧客情報を削除します.
   *
   * @param customerNo 顧客番号
   * @return 削除件数
   */
  int deleteCustomer(String customerNo);

}
