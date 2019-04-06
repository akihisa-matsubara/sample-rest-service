package jp.co.jaxrs.sample.pres.resource;

import jp.co.jaxrs.framework.code.ResultVo;
import jp.co.jaxrs.framework.constant.CommonReqParam;
import jp.co.jaxrs.framework.pres.dto.ResponseDto;
import jp.co.jaxrs.framework.util.QueryBuilder;
import jp.co.jaxrs.sample.biz.logic.CustomerService;
import jp.co.jaxrs.sample.common.constant.ReqParam;
import jp.co.jaxrs.sample.common.dto.CustomerDto;
import jp.co.jaxrs.sample.pres.SampleApplication;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
 * <PRE>
 * 顧客リソース.
 * APIのURLベースパスやAPI内で指定する書式フォーマットについては、
 * {@link SampleApplication}参照してください.
 * </PRE>
 */
@Path("/customers")
public class CustomerResource {

  /** 顧客サービス. */
  @Inject
  private CustomerService customerService;

  /**
   * 顧客情報を取得します.
   * オプションのフィルターでは、"="演算子のみ対応していますが、"<"や">"といたった範囲の指定やLIKEによるあいまい検索には対応していません.
   *
   * @param offset Offset
   * @param limit 取得件数
   * @param sort ソート条件
   * @param nameKanji 氏名漢字
   * @param nameKana 氏名カナ
   * @param gender 性別
   * @param birthday 生年月日
   * @param addressZip 郵便番号
   * @param address 住所
   * @return {@link ResponseDto} Response Dto(顧客情報)
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public ResponseDto getCustomers(
      @DefaultValue("0") @QueryParam(CommonReqParam.OFFSET) int offset,
      @DefaultValue("100") @QueryParam(CommonReqParam.LIMIT) int limit,
      @QueryParam(CommonReqParam.SORT) String sort,
      @QueryParam(ReqParam.NAME_KANJI) String nameKanji,
      @QueryParam(ReqParam.NAME_KANA) String nameKana,
      @QueryParam(ReqParam.GENDER) String gender,
      @QueryParam(ReqParam.BIRTHDAY) Date birthday,
      @QueryParam(ReqParam.ADDRESS_ZIP) String addressZip,
      @QueryParam(ReqParam.ADDRESS) String address) {

    // XXX 格納順番がそのままwhere節の出力順となる為、INDEXを考慮すること（ただし、多数の条件でOptimizerが正しい実行計画を選択するかは不明）
    Map<String, Object> queryParams = new LinkedHashMap<>();
    QueryBuilder.putParam(queryParams, ReqParam.NAME_KANJI, nameKanji);
    QueryBuilder.putParam(queryParams, ReqParam.NAME_KANA, nameKana);
    QueryBuilder.putParam(queryParams, ReqParam.GENDER, gender);
    QueryBuilder.putParam(queryParams, ReqParam.BIRTHDAY, birthday);
    QueryBuilder.putParam(queryParams, ReqParam.ADDRESS_ZIP, addressZip);
    QueryBuilder.putParam(queryParams, ReqParam.ADDRESS, address);

    List<CustomerDto> customers = customerService.getCustomers(QueryBuilder.createSearchCondition(offset, limit, sort, queryParams));
    return ResponseDto.builder()
        .result(ResultVo.SUCCESS.getDecode())
        .response(customers)
        .build();
  }

  /**
   * 指定した顧客情報を取得します.
   *
   * @param customerNo 顧客番号
   * @return {@link ResponseDto} Response Dto(顧客情報)
   */
  @Path("/{" + ReqParam.CUSTOMER_NO + "}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public ResponseDto getCustomer(@PathParam(ReqParam.CUSTOMER_NO) @Size(min = 8, max = 8) String customerNo) {
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
   * @return {@link ResponseDto} Response Dto
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
   * @return {@link ResponseDto} Response Dto(更新件数)
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
   * @return {@link ResponseDto} Response Dto(削除件数)
   */
  @Path("/{" + ReqParam.CUSTOMER_NO + "}")
  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  public ResponseDto deleteCustomer(@PathParam(ReqParam.CUSTOMER_NO) @Size(min = 8, max = 8) String customerNo) {
    int deleteCount = customerService.deleteCustomer(customerNo);
    return ResponseDto.builder()
        .result(ResultVo.SUCCESS.getDecode())
        .response(deleteCount)
        .build();
  }

}
