package jp.co.sample.rest.pres.resource;

import jp.co.sample.common.code.DateFormat.DateFormatVo;
import jp.co.sample.common.code.GenderVo;
import jp.co.sample.common.util.LocalDateFormatUtils;
import jp.co.sample.rest.common.constant.ReqParam;
import jp.co.sample.rest.common.dto.CustomerDto;
import jp.co.sample.rest.framework.code.ResultVo;
import jp.co.sample.rest.framework.constant.CommonReqParam;
import jp.co.sample.rest.framework.pres.dto.ResponseDto;
import jp.co.sample.rest.pres.SampleApplication;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * <PRE>
 * 顧客リソース(外部システム想定).
 * APIのURLベースパスやAPI内で指定する書式フォーマットについては、
 * {@link SampleApplication}参照してください.
 * </PRE>
 */
@Path("/external/customers")
public class ExternalCustomerResource {

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
  public ResponseDto<List<CustomerDto>> getCustomers(
      @DefaultValue("0") @QueryParam(CommonReqParam.OFFSET) int offset,
      @DefaultValue("100") @QueryParam(CommonReqParam.LIMIT) int limit,
      @QueryParam(CommonReqParam.SORT) String sort,
      @QueryParam(ReqParam.NAME_KANJI) String nameKanji,
      @QueryParam(ReqParam.NAME_KANA) String nameKana,
      @QueryParam(ReqParam.GENDER) String gender,
      @QueryParam(ReqParam.BIRTHDAY) LocalDate birthday,
      @QueryParam(ReqParam.ADDRESS_ZIP) String addressZip,
      @QueryParam(ReqParam.ADDRESS) String address) {

    CustomerDto externalCustomer = new CustomerDto();
    externalCustomer.setCustomerNo("E1234567");
    externalCustomer.setNameKanji("外部　顧客");
    externalCustomer.setNameKana("ガイブ　コキャク");
    externalCustomer.setGender(GenderVo.MALE.getCode());
    externalCustomer.setBirthday(LocalDateFormatUtils.parse("2004-05-12", DateFormatVo.YYYYMMDD));
    externalCustomer.setAddressZip("1234567");
    externalCustomer.setAddress("神奈川県横浜市");

    List<CustomerDto> customers = new ArrayList<>();
    customers.add(externalCustomer);

    return ResponseDto.<List<CustomerDto>>builder()
        .result(ResultVo.SUCCESS.getDecode())
        .response(customers)
        .build();
  }

}
