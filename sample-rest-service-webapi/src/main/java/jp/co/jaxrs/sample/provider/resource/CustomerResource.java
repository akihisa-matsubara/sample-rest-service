package jp.co.jaxrs.sample.provider.resource;

import jp.co.jaxrs.sample.common.data.entity.CustomerEntity;
import jp.co.jaxrs.sample.provider.requestdto.CustomerDto;
import jp.co.jaxrs.sample.service.CustomerService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 顧客リソース.
 */
@Path("/customers")
public class CustomerResource {

  /** 顧客サービス. */
  @Inject
  private CustomerService customerService;

  /**
   * 顧客情報を取得します.
   *
   * @return 顧客情報
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<CustomerEntity> getCustomers() {
    return customerService.getCustomers();
  }

  /**
   * 指定した顧客情報を取得します.
   *
   * @param customerNo 顧客番号
   * @return 顧客情報
   */
  @Path("{customerNo}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public CustomerEntity getCustomer(@PathParam("customerNo") String customerNo) {
    return customerService.getCustomer(customerNo);
  }

  /**
   * 顧客情報を作成します.
   *
   * @param formList 顧客情報
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void createCustomer(List<CustomerDto> formList) {
    formList.forEach(form -> customerService.createCustomer(form));
  }

  /**
   * 顧客情報を更新します.
   *
   * @param formList 顧客情報
   * @return 更新件数
   */
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  public int updateCustomer(List<CustomerDto> formList) {
    int updateCount = 0;
    for (CustomerDto form : formList) {
      updateCount = updateCount + customerService.updateCustomer(form);
    }
    return updateCount;
  }

  /**
   * 指定した顧客情報を削除します.
   *
   * @param customerNo 顧客番号
   */
  @Path("{customerNo}")
  @DELETE
  public void deleteCustomer(@PathParam("customerNo") String customerNo) {
    customerService.deleteCustomer(customerNo);
  }

}
