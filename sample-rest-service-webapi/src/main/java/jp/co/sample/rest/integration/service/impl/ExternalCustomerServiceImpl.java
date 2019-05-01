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
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.jaxrs.utils.ExceptionUtils;

/**
 * 外部顧客サービス実装.
 */
@ApplicationScoped
@Slf4j
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

    try {
      CustomersResponseDto responseDto = target.request(MediaType.APPLICATION_JSON_TYPE).get(CustomersResponseDto.class);

      if (ResultVo.SUCCESS.getDecode().equals(responseDto.getResult())) {
        return responseDto.getResponse();
      }

    } catch (Exception e) {
      // 外部サービス呼び出し時に例外が発生しても業務継続する
      log.warn(ExceptionUtils.getStackTrace(e));

    }

    return Collections.emptyList();

  }

}
