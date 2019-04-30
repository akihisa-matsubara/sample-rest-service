package jp.co.sample.rest.framework.provider.exceptionmapper;

import jp.co.sample.rest.framework.code.LoggerVo;
import jp.co.sample.rest.framework.code.ResultVo;
import jp.co.sample.rest.framework.pres.dto.ResponseDto;
import java.util.List;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基底例外Mapper.
 *
 * @param <EM> ExceptionMapper
 * @param <E> Exception
 */
@Slf4j
public abstract class BaseExceptionMapper<EM, E extends Exception> {

  /** Error Logger. */
  private static final Logger ERROR_LOGGER = LoggerFactory.getLogger(LoggerVo.ERROR_LOGGER.getCode());

  /**
   * 例外をResponseに設定します.
   *
   * @param exception 例外
   * @return {@link Response} 例外を設定したResponse
   */
  public Response toResponse(E exception) {
    log.error(ExceptionUtils.getStackTrace(exception));
    ERROR_LOGGER.error(ExceptionUtils.getStackTrace(exception));

    ResponseDto responseDto = ResponseDto.builder()
        .result(ResultVo.FAILURE.getDecode())
        .errors(getErrors(exception))
        .build();

    return Response.status(getStatus()).entity(responseDto).build();
  }

  /**
   * Classを取得します.
   *
   * @return Class クラス
   */
  public abstract Class<EM> getClassType();

  /**
   * エラー情報を取得します.
   *
   * @param exception 例外
   * @return エラー情報
   */
  public abstract List<String> getErrors(E exception);

  /**
   * HTTPステータスを取得します.
   *
   * @return {@link Status} HTTPステータス
   */
  public abstract Status getStatus();

}
