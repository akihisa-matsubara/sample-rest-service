package jp.co.jaxrs.framework.exception.mapper;

import jp.co.jaxrs.framework.code.ResultVo;
import jp.co.jaxrs.framework.pres.dto.ResponseDto;
import java.util.ArrayList;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * JAX-RS の汎用基底例外Mapper.
 */
@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

  /**
   * 例外をResponseに設定します.
   *
   * @return 例外を設定したResponse
   */
  @Override
  public Response toResponse(WebApplicationException exception) {
    ResponseDto responseDto = ResponseDto.builder()
        .result(ResultVo.FAILURE.getDecode())
        .errors(new ArrayList<>())
        .build();

    responseDto.getErrors().add(exception.getMessage());

    return Response.status(Status.INTERNAL_SERVER_ERROR).entity(responseDto).build();
  }

}
