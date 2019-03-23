package jp.co.jaxrs.sample.pres.resource;

import jp.co.jaxrs.framework.code.ResultVo;
import jp.co.jaxrs.framework.pres.dto.ResponseDto;
import jp.co.jaxrs.sample.biz.logic.CustomerService;
import jp.co.jaxrs.sample.common.dto.CustomerDto;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Size;
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
   * @return Response Dto(顧客情報)
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public ResponseDto getCustomers() {
    List<CustomerDto> customers = customerService.getCustomers();
    return ResponseDto.builder()
        .result(ResultVo.SUCCESS.getDecode())
        .response(customers)
        .build();
  }

  /**
   * 指定した顧客情報を取得します.
   *
   * @param customerNo 顧客番号
   * @return Response Dto(顧客情報)
   */
  @Path("{customerNo}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public ResponseDto getCustomer(@PathParam("customerNo") @Size(min = 8, max = 8) String customerNo) {
    CustomerDto customer = customerService.getCustomer(customerNo);
    return ResponseDto.builder()
        .result(ResultVo.SUCCESS.getDecode())
        .response(customer)
        .build();
  }

  /**
   * 顧客情報を作成します.
   *
   * @param formList 顧客情報
   * @return Response Dto
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ResponseDto createCustomer(@Valid List<CustomerDto> formList) {
    formList.forEach(customerService::createCustomer);
    return ResponseDto.builder()
        .result(ResultVo.SUCCESS.getDecode())
        .build();
  }

  /**
   * 顧客情報を更新します.
   *
   * @param formList 顧客情報
   * @return Response Dto(更新件数)
   */
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ResponseDto updateCustomer(@Valid List<CustomerDto> formList) {
    int updateCount = formList.stream().mapToInt(customerService::updateCustomer).sum();
    return ResponseDto.builder()
        .result(ResultVo.SUCCESS.getDecode())
        .response(updateCount)
        .build();
  }

  /**
   * 指定した顧客情報を削除します.
   *
   * @param customerNo 顧客番号
   * @return Response Dto(削除件数)
   */
  @Path("{customerNo}")
  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  public ResponseDto deleteCustomer(@PathParam("customerNo") @Size(min = 8, max = 8) String customerNo) {
    int deleteCount = customerService.deleteCustomer(customerNo);
    return ResponseDto.builder()
        .result(ResultVo.SUCCESS.getDecode())
        .response(deleteCount)
        .build();
  }

}
