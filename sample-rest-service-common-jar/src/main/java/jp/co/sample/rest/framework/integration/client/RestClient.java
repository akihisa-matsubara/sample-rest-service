package jp.co.sample.rest.framework.integration.client;

import jp.co.sample.rest.framework.provider.JsonProvider;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Rest Client.
 * Clientはthread safe.
 */
@ApplicationScoped
public class RestClient {

  /** Client. */
  private Client client;

  /**
   * 初期化.
   */
  @PostConstruct
  public void initialize() {
    client = ClientBuilder.newClient();
    client.register(JsonProvider.class);
  }

  /**
   * Clientインスタンスを取得します.
   *
   * @return Client
   */
  public Client getInstance() {
    return client;
  }

}
