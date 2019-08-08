package jp.co.sample.rest.integration.service.impl;

import jp.co.sample.rest.common.constant.StatisticsProcess;
import jp.co.sample.rest.common.dto.CustomerDto;
import jp.co.sample.rest.framework.code.ResultVo;
import jp.co.sample.rest.framework.config.Config;
import jp.co.sample.rest.framework.interceptor.UsageStatistics;
import jp.co.sample.rest.framework.util.ClientWrapper;
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
  @UsageStatistics(processName = StatisticsProcess.EXTENAL_CUSTOMER_SERVICE)
  @Override
  public List<CustomerDto> getCustomers() {
    CustomersResponseDto responseDto = ClientWrapper.getWithPath(externalServiceBaseUrl, customersApi, CustomersResponseDto.class);

    if (responseDto != null && ResultVo.SUCCESS.getDecode().equals(responseDto.getResult())) {
      return responseDto.getResponse();
    }

    return Collections.emptyList();

  }

}
