package dev.sample.rest.pres.resource;

import dev.sample.framework.rest.constant.CommonReqParam;
import dev.sample.framework.rest.pres.dto.ResponseBaseDto;
import dev.sample.framework.rest.pres.resource.ResourceBase;
import dev.sample.framework.rest.util.SearchConditionBuilder;
import dev.sample.rest.biz.logic.CustomerService;
import dev.sample.rest.common.constant.ReqParam;
import dev.sample.rest.common.dto.CustomerDto;
import java.time.LocalDate;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * 顧客リソース.
 */
@SuppressWarnings("unchecked")
@Path("/customers")
public class CustomerResource implements ResourceBase {

  /** 顧客サービス. */
  @Inject
  private CustomerService customerService;

  /**
   * 顧客情報を取得します.
   * 本APIのみ併せて外部サービスから顧客情報を取得します.
   * オプションのフィルターでは、"="演算子のみ対応していますが、"<"や">"といった範囲の指定やLIKEによるあいまい検索には対応していません.
   *
   * @param offset Offset
   * @param limit 取得件数
   * @param sort ソート条件
   * @param nameKanji 氏名漢字
   * @param nameKana 氏名カナ
   * @param gender 性別
   * @param birthday 生年月日
   * @param telNo 電話番号
   * @param addressZip 郵便番号
   * @param address 住所
   * @param email Eメール
   * @return {@link ResponseBaseDto} Response Dto(顧客情報)
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public ResponseBaseDto<List<CustomerDto>> getCustomers(
      @DefaultValue("0") @QueryParam(CommonReqParam.OFFSET) int offset,
      @DefaultValue("100") @QueryParam(CommonReqParam.LIMIT) int limit,
      @QueryParam(CommonReqParam.SORT) String sort,
      @QueryParam(ReqParam.NAME_KANJI) String nameKanji,
      @QueryParam(ReqParam.NAME_KANA) String nameKana,
      @QueryParam(ReqParam.GENDER) String gender,
      @QueryParam(ReqParam.BIRTHDAY) LocalDate birthday,
      @QueryParam(ReqParam.TEL_NO) String telNo,
      @QueryParam(ReqParam.ADDRESS_ZIP) String addressZip,
      @QueryParam(ReqParam.ADDRESS) String address,
      @QueryParam(ReqParam.EMAIL) String email) {

    SearchConditionBuilder builder = new SearchConditionBuilder(offset, limit, sort)
        .putParam(ReqParam.NAME_KANJI, nameKanji)
        .putParam(ReqParam.NAME_KANA, nameKana)
        .putParam(ReqParam.GENDER, gender)
        .putParam(ReqParam.BIRTHDAY, birthday)
        .putParam(ReqParam.TEL_NO, telNo)
        .putParam(ReqParam.ADDRESS_ZIP, addressZip)
        .putParam(ReqParam.ADDRESS, address)
        .putParam(ReqParam.EMAIL, email);

    List<CustomerDto> customers = customerService.getCustomers(builder.build());
    return ResourceBase.createResponse(customers);
  }

  /**
   * 指定した顧客情報を取得します.
   *
   * @param customerNo 顧客番号
   * @return {@link ResponseBaseDto} Response Dto(顧客情報)
   */
  @Path("/{" + ReqParam.CUSTOMER_NO + "}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public ResponseBaseDto<CustomerDto> getCustomer(@PathParam(ReqParam.CUSTOMER_NO) @Size(min = 8, max = 8) String customerNo) {
    CustomerDto customer = customerService.getCustomer(customerNo);
    return ResourceBase.createResponse(customer);
  }

  /**
   * 顧客情報を作成します.
   *
   * @param formList 顧客情報
   * @return {@link ResponseBaseDto} Response Dto
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ResponseBaseDto<Object> createCustomer(@Valid List<CustomerDto> formList) {
    formList.forEach(customerService::createCustomer);
    return ResourceBase.createResponse();
  }

  /**
   * 顧客情報を更新します.
   *
   * @param formList 顧客情報
   * @return {@link ResponseBaseDto} Response Dto(更新件数)
   */
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ResponseBaseDto<Integer> updateCustomer(@Valid List<CustomerDto> formList) {
    int updateCount = formList.stream()
        .mapToInt(customerService::updateCustomer)
        .sum();
    return ResourceBase.createResponse(updateCount);
  }

  /**
   * 指定した顧客情報を削除します.
   *
   * @param customerNo 顧客番号
   * @return {@link ResponseBaseDto} Response Dto(削除件数)
   */
  @Path("/{" + ReqParam.CUSTOMER_NO + "}")
  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  public ResponseBaseDto<Integer> deleteCustomer(@PathParam(ReqParam.CUSTOMER_NO) @Size(min = 8, max = 8) String customerNo) {
    int deleteCount = customerService.deleteCustomer(customerNo);
    return ResourceBase.createResponse(deleteCount);
  }

}
