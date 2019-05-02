package jp.co.sample.rest.framework.pres.resource;

import jp.co.sample.rest.framework.code.ResultVo;
import jp.co.sample.rest.framework.pres.dto.ResponseDto;

/**
 * <PRE>
 * リソース基底インターフェース.
 * APIのURLベースパスやAPI内で指定する書式フォーマットについては、
 * {@link SampleApplication}参照してください.
 * </PRE>
 */
public interface ResourceBase {

  /**
   * ResponseDtoを作成します.
   *
   * @param <T> response
   * @param response response
   * @return ResponseDto
   */
  @SuppressWarnings("rawtypes")
  public static <T> ResponseDto createResponse(T response) {
    return ResponseDto.<T>builder()
        .result(ResultVo.SUCCESS.getDecode())
        .response(response)
        .build();
  }

}
