package jp.co.jaxrs.framework.exception.mapper;

import jp.co.jaxrs.framework.code.LoggerVo;
import jp.co.jaxrs.framework.code.ResultVo;
import jp.co.jaxrs.framework.pres.dto.ResponseDto;
import java.util.ArrayList;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 想定外例外Mapper.
 */
@Provider
public class IllegalExceptionMapper implements ExceptionMapper<Exception> {

  /** Debug Logger. */
  private static final Logger DEBUG_LOGGER = LoggerFactory.getLogger(IllegalExceptionMapper.class);
  /** Error Logger. */
  private static final Logger ERROR_LOGGER = LoggerFactory.getLogger(LoggerVo.ERROR_LOGGER.getCode());

  /**
   * 例外をResponseに設定します.
   *
   * @return {@link Response} 例外を設定したResponse
   */
  @Override
  public Response toResponse(Exception exception) {
    DEBUG_LOGGER.error(ExceptionUtils.getStackTrace(exception));
    ERROR_LOGGER.error(ExceptionUtils.getStackTrace(exception));

    ResponseDto responseDto = ResponseDto.builder()
        .result(ResultVo.FAILURE.getDecode())
        .errors(new ArrayList<>())
        .build();

    responseDto.getErrors().add(exception.getMessage());

    return Response.status(Status.INTERNAL_SERVER_ERROR).entity(responseDto).build();
  }

}
