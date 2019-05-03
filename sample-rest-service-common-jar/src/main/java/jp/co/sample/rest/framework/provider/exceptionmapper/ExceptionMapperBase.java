package jp.co.sample.rest.framework.provider.exceptionmapper;

import jp.co.sample.rest.framework.code.LoggerVo;
import jp.co.sample.rest.framework.code.ResultVo;
import jp.co.sample.rest.framework.pres.dto.ResponseBaseDto;
import java.util.List;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基底例外Mapper.
 *
 * @param <EM> ExceptionMapper
 * @param <E> Exception
 */
public abstract class ExceptionMapperBase<EM, E extends Exception> {

  /** Error Logger. */
  protected static final Logger ERROR_LOGGER = LoggerFactory.getLogger(LoggerVo.ERROR_LOGGER.getCode());

  /**
   * 例外をResponseに設定します.
   *
   * @param exception 例外
   * @return {@link Response} 例外を設定したResponse
   */
  protected Response toResponse(E exception) {
    // implements先でログ出力
    ResponseBaseDto<Object> responseDto = ResponseBaseDto.builder()
        .result(ResultVo.FAILURE.getDecode())
        .errors(getErrors(exception))
        .build();

    return Response.status(getStatusCode(exception)).entity(responseDto).build();
  }

  /**
   * Classを取得します.
   *
   * @return Class クラス
   */
  protected abstract Class<EM> getClassType();

  /**
   * エラー情報を取得します.
   *
   * @param exception 例外
   * @return エラー情報
   */
  protected abstract List<String> getErrors(E exception);

  /**
   * HTTPステータスコードを取得します.
   *
   * @param exception 例外
   * @return {@link Status} HTTPステータスコード
   */
  protected abstract int getStatusCode(E exception);

}
