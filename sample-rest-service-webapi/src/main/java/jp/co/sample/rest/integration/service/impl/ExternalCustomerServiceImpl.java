package jp.co.sample.rest.integration.service.impl;

import jp.co.sample.rest.common.dto.CustomerDto;
import jp.co.sample.rest.framework.code.ResultVo;
import jp.co.sample.rest.framework.config.Config;
import jp.co.sample.rest.framework.integration.client.RestClient;
import jp.co.sample.rest.integration.dto.CustomersResponseDto;
import jp.co.sample.rest.integration.service.ExternalCustomerService;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * 外部顧客サービス実装.
 */
@ApplicationScoped
public class ExternalCustomerServiceImpl implements ExternalCustomerService {

  /** Rest Client. */
  @Inject
  private RestClient restClient;

  /** 外部サービス基底URL. */
  @Config
  @Inject
  private String externalServiceBaseUrl;

  /** 外部サービスURL. */
  @Config
  @Inject
  private String externalCustomersServiceUrl;

  /**
   * {@inheritDoc}
   */
  @Override
  public List<CustomerDto> getCustomers() {
    Client client = restClient.getInstance();
    WebTarget target = client.target(externalServiceBaseUrl).path(externalCustomersServiceUrl);
    CustomersResponseDto responseDto = target.request(MediaType.APPLICATION_JSON_TYPE).get(CustomersResponseDto.class);

    if (ResultVo.SUCCESS.getDecode().equals(responseDto.getResult())) {
      return responseDto.getResponse();
    }
    return Collections.emptyList();

  }

}
