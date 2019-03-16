package jp.co.jaxrs.framework.exception.mapper;

import jp.co.jaxrs.framework.code.ResultVo;
import jp.co.jaxrs.framework.pres.dto.ResponseDto;
import java.util.ArrayList;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * 想定外例外Mapper.
 */
@Provider
public class IllegalExceptionMapper implements ExceptionMapper<Exception> {

  /**
   * 例外をResponseに設定します.
   *
   * @return 例外を設定したResponse
   */
  @Override
  public Response toResponse(Exception exception) {
    ResponseDto responseDto = ResponseDto.builder()
        .result(ResultVo.FAILURE.getDecode())
        .errors(new ArrayList<>())
        .build();

    responseDto.getErrors().add(exception.getMessage());

    return Response.status(Status.INTERNAL_SERVER_ERROR).entity(responseDto).build();
  }

}
