package uit.dev.sample.rest.pres.resource;

import static org.assertj.core.api.Assertions.*;
import dev.sample.rest.test.util.ArquillianTestUtils;
import dev.sample.rest.test.util.RestClientTestUtils;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Customers API テストクラス.
 */
// TODO Arquillian は現在JUnit5対応中のためJUnit4として動かす。対応され次第JUnit5ベースに修正する。
// https://github.com/arquillian/arquillian-core/issues/137
@RunWith(Arquillian.class)
public class CustomersArquillianTest {

  private static final String BASE_URL = "http://localhost:9080/sample-rest-service-webapi/";
  private static final String CUSTOMERS_API = "api/v1/sample/customers";

  @Deployment(testable = true)
  public static WebArchive createDeployment() {
    return ArquillianTestUtils.createWebArchive(CustomersArquillianTest.class);
  }

  @Test
  @RunAsClient
  @InSequence(1)
  public void testCustomersEndpoints() throws Exception {
    // --- setup -----
    Client client = RestClientTestUtils.newClient();

    // --- execute ---
    // Cutomers API [GET] /cutomers
    Response response = client.target(BASE_URL + CUSTOMERS_API).request(MediaType.APPLICATION_JSON_TYPE).get();
    System.out.println("response:" + response.readEntity(String.class));
    // JsonObject jsonResponse = response.readEntity(JsonObject.class);

    // --- verify ----
    // TODO create custom assertion (ResponseAssert) class
    assertThat(response.getStatus()).as("HTTPステータスコードが200であること").isEqualTo(200);
  }

}
