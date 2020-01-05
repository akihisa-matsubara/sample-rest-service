package jp.co.sample.rest.integration.service.impl;

import jp.co.sample.framework.core.code.ResultVo;
import jp.co.sample.framework.core.config.Config;
import jp.co.sample.framework.core.interceptor.UsageStatistics;
import jp.co.sample.framework.rest.util.ClientWrapper;
import jp.co.sample.rest.common.constant.ProcessName;
import jp.co.sample.rest.common.dto.CustomerDto;
import jp.co.sample.rest.integration.dto.CustomersResponseDto;
import jp.co.sample.rest.integration.service.ExternalCustomerService;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * 外部顧客サービス実装.
 */
@ApplicationScoped
public class ExternalCustomerServiceImpl implements ExternalCustomerService {

  /** 外部サービス基底URL. */
  @Config
  @Inject
  private String externalServiceBaseUrl;

  /** 外部サービスURL. */
  @Config
  @Inject
  private String customersApi;

  /**
   * {@inheritDoc}
   */
  @UsageStatistics(processName = ProcessName.EXTENAL_CUSTOMER_SERVICE)
  @Override
  public List<CustomerDto> getCustomers() {
    CustomersResponseDto responseDto = ClientWrapper.getWithPath(externalServiceBaseUrl, customersApi, CustomersResponseDto.class);

    if (responseDto != null && ResultVo.SUCCESS.getDecode().equals(responseDto.getResult())) {
      return responseDto.getResponse();
    }

    return Collections.emptyList();

  }

}
