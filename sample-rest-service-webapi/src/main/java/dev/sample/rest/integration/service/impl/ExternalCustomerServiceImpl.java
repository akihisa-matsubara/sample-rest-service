package dev.sample.rest.integration.service.impl;

import dev.sample.framework.core.code.ResultVo;
import dev.sample.framework.core.config.Config;
import dev.sample.framework.core.interceptor.UsageStatistics;
import dev.sample.framework.rest.util.ClientWrapper;
import dev.sample.rest.common.constant.ProcessName;
import dev.sample.rest.common.dto.CustomerDto;
import dev.sample.rest.integration.dto.CustomersResponseDto;
import dev.sample.rest.integration.service.ExternalCustomerService;
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
  @Config("sample.restService.externalServiceBaseUrl")
  @Inject
  private String externalServiceBaseUrl;

  /** 外部サービスURL. */
  @Config("sample.restService.customersApi")
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
